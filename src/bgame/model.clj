(ns bgame.model
  (:require [clojure.spec.alpha :as s]
            [bgame.vec :as v])
  (:import (bgame.vec V)))

(s/def ::id (s/or ::number int? ::name string?))
(s/def ::position v/v?)
(s/def ::speed v/v?)
(s/def ::d0-1 (s/and double? #(<= 0 % 1)))

(s/def ::poisoned ::d0-1)
(s/def ::energy ::d0-1)
(s/def ::invisible ::d0-1)

(s/def ::moving-object (s/keys :req
                               [::id
                                ::position
                                ::speed
                                ::energy]))
;:opt [:acct/phone]))

(s/def ::pray (s/merge ::moving-object
                       (s/keys :req [::poisoned])))
(s/def ::hunter (s/merge ::moving-object
                         (s/keys :req [::invisible])))
(s/def ::hunter-players (s/* ::hunter))
(s/def ::hunter-auto (s/* ::hunter))
(s/def ::prays (s/* ::pray))
(s/def :game/world (s/keys :req
                           [::hunter-players
                            ::prays]))

(comment
  (s/explain ::moving-object {::id       12
                              ::position (V. 12 23)
                              ::speed    (V. 12 2)})
  (s/explain ::world
             {::hunter-player {::id       12
                               ::position (V. 12 23)
                               ::speed    (V. 12 2)}
              ::prays         [{::id       12
                                ::position (V. 12 23)
                                ::speed    (V. 12 2)
                                ::poisoned true}
                               {::id       12
                                ::position (V. 12 23)
                                ::speed    (V. 12 2)
                                ::poisoned false}]}))

;; example of init data

(def example-init
  {:player-hunters    2
   :prays      40
   :game-size (V. 1500 700)})


(defn initial-example-model
  "model to start with - e.g. for testing and for playing a very simple test game"
  [init-map]
  (let [game-size   (:game-size init-map)
        num-hunters (:player-hunters init-map)
        num-pray (:prays init-map)
        hunters (mapv (fn [num] {::id        num
                                 ::position  (V. num num)
                                 ::speed     (V. num num)
                                 ::invisible 0.5})
                      (range num-hunters))
        prays (mapv (fn [num] {::id        num
                               ::position  (v/v-minus game-size (V. num num))
                               ::speed     (V. (- num) (- num))
                               ::poisoned 0.1})
                    (range num-hunters (+ num-hunters num-pray)))]
    {::player-hunters    hunters
     ::prays prays
     :game-size (:game-size init-map)}))
