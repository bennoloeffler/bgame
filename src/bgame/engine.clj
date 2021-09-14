(ns bgame.engine
  (:require [clojure.spec.alpha :as s]
            [bgame.vec  :as v]
            [bgame.model  :as m]))



;;
;; ---------- HELPERs -----------
;;

(defn move-object [o]
  {:pre (s/valid? :m/moving-object o)}
  (update o ::m/position v/v-add (o ::m/speed)))

;;
;; ---------- ENGINE behaviour -----------
;;

(defn move-pray [world input]
  [world input])

(defn move-auto-hunter [world input]
  [world input])

(defn move-player-hunter [world input]
  [world input])

(defn near-hunters-damage [world input]
  [world input])

(defn near-pray-energy [world input]
  [world input])

(defn near-pray-poison [world input]
  [world input])

(defn poison-hunters [world input]
  [world input])


(def ^:dynamic *engine-parts*
  (atom [move-pray
         move-auto-hunter
         move-player-hunter
         near-hunters-damage
         near-pray-energy
         near-pray-poison
         poison-hunters]))


;;
;; ---------- ENGINE core -----------
;;

(defn get-user-input
  []
  {})

(defn next-step
  "calcs the next state of the entities to be shown"
  [[world input]]
  {:pre  (s/valid? :m/world world)
   :post (s/valid? :m/world %)}
  (reduce (fn [[world input] f] (f world input))
          [world input]
          @*engine-parts*))

(defn game-over [[world input]]
  false)

(defn run [world]
  (loop [[world input] [world {}]]
    (if (not (game-over [world input]))
      (recur (next-step [world (get-user-input)])))))


