(ns bgame.model
  (:require [clojure.spec.alpha :as s]
            [bgame.vec :refer :all])
  (:import (bgame.vec V)))

(s/def ::id (s/or ::number int? ::name string?))
(s/def ::position v?)
(s/def ::speed v?)
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
(s/def ::hunter-player (s/* ::moving-object))
(s/def ::hunter-auto (s/* ::moving-object))
(s/def ::prays (s/* ::pray))
(s/def :game/world (s/keys :req
                           [::hunter-player
                            ::prays]))

(comment
  (s/explain ::moving-object {::id       12
                              ::position (V. 12 23)
                              ::speed    (V. 12 2)})
  (s/explain ::world
             {::hunter-player   {::id       12
                                 ::position (V. 12 23)
                                 ::speed    (V. 12 2)}
              ::prays [{::id       12
                        ::position (V. 12 23)
                        ::speed    (V. 12 2)
                        ::poisoned true}
                       {::id       12
                        ::position (V. 12 23)
                        ::speed    (V. 12 2)
                        ::poisoned false}]}))

;; example of init data

(def example-init
  {:monsters  2
   :game-size (V. 1500 700)})

;; example of game state

#_(def example-model
    {:game-size (V. 1000 500)
     :monsters  [{::id "willi" ::position (V. 12 15) ::speed (V. 2 6)}]})

(defn initial-model
  "model to start with"
  [init-map]
  (let [monsters (mapv (fn [num] {::id       num
                                  ::position (V. num num)
                                  ::speed    (V. num num)
                                  ::poisoned false})
                       (range (:monsters init-map)))]
    {:monsters  monsters
     :player    {::id       -1
                 ::position (V. 1 1)
                 ::speed    (V. 1 1)}
     :game-size (:game-size init-map)}))
