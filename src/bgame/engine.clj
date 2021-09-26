(ns bgame.engine
  (:use tupelo.core)
  (:require [clojure.spec.alpha :as s]
            [belib.vec :as v]
            [bgame.model :as m]
            [clojure.core.async :refer [chan >!! <!!]]))



;;
;; ---------- HELPERs for objects or collection of objects -----------
;;

(defn in-world? [world position]
  (v/v-in-area? 0 0 (-> world ::m/game-size :x) (-> world ::m/game-size :y)
                position))

(defn distance [o1 o2]
  {:pre [(s/valid? ::m/moving-object o1)
         (s/valid? ::m/moving-object o2)]}
  (v/v-distance (::m/position o1) (::m/position o2)))

(defn move-object [world input o]
  {:pre [(s/valid? ::m/moving-object o)
         (in-world? world (-> o ::m/position))]
   :post (in-world? world (-> % ::m/position))}
  (let [old-pos (o ::m/position)
        new-pos (v/v-add old-pos (o ::m/speed))]
   (if (in-world? world new-pos)
       (update o ::m/position v/v-add (o ::m/speed))
       (update o ::m/speed v/v-rotate (v/v-grad-to-pi (rand-int 360))))))

(defn move-objects
  "move not only one - but a seq"
  [world input seq-of-objects]
  (map #(move-object world input %) seq-of-objects))

(defn move-a-sequence
  "keyword-of-sequence may be:
   ::m/prays
   ::m/player-hunters
   ::m/auto-hunters
   or other sequences of movable objects"
  [world input keyword-of-sequence]
  (let [moved-group (move-objects world input (keyword-of-sequence world))]
    [(assoc world keyword-of-sequence moved-group) input]))

;;
;; ---------- ENGINE behaviour -----------
;;


(defn move-prays [world input]
  (move-a-sequence world input ::m/prays)
  #_(let [moved-pray (move-objects (::m/prays world))]
      [(assoc world ::m/prays moved-pray) input]))

(defn move-auto-hunters [world input]
  (move-a-sequence world input ::m/auto-hunters))

(defn move-player-hunters [world input]
  (move-a-sequence world input ::m/player-hunters))


#_(defn near-hunters-damage [world input]
    [world input])

#_(defn near-pray-energy [world input]
    [world input])

#_(defn near-pray-poison [world input]
    [world input])

#_(defn poison-hunters [world input]
    [world input])


(def ^:dynamic *engine-parts*
  (atom [move-prays
         move-auto-hunters
         move-player-hunters
         #_near-hunters-damage ;
         #_near-pray-energy
         #_near-pray-poison
         #_poison-hunters]))


;;
;; ---------- ENGINE core -----------
;;

(defn get-user-input
  []
  {})

(defn next-step
  "calcs the next state of the entities to be shown"
  [[world input]]
  {:pre  (s/valid? ::m/world world)
   :post (s/valid? ::m/world %)}
  (reduce (fn [[world input] f] (f world input))
          [world input]
          @*engine-parts*))

(defn game-over [[world input]]
  false)

(defn run [world]
  (let [channel (chan)]
   (future
    (loop [[world input] [world {}]]
      (if (not (game-over [world input]))
       (do
         (>!! channel world)
         (recur (next-step [world (get-user-input)]))))))
   channel))

(defn start-it []
  (let [c (run bgame.model/initial-model)]
    (doseq [idx (range 10000)]
      (print ".")
      (<!! c))))


(comment
  (time (start-it)))