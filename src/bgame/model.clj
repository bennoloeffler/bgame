(ns bgame.model
  (:require [clojure.spec.alpha :as s]
            [belib.vec :as v])
  (:import (belib.vec V)))

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
(s/def ::player-hunters (s/* ::hunter))
(s/def ::auto-hunters (s/* ::hunter))
(s/def ::prays (s/* ::pray))

(s/def ::game-size v/v?)
(s/def ::world (s/keys :req
                       [::player-hunters
                        ::prays
                        ::game-size]))


(def initial-model
  {::player-hunters [{::id        1
                      ::position  (V. 1 1)
                      ::speed     (V. 1 1)
                      ::energy    0.8
                      ::invisible 0.7}
                     {::id        1
                      ::position  (V. 1 1)
                      ::speed     (V. 1 1)
                      ::energy    0.8
                      ::invisible 0.7}
                     {::id        1
                      ::position  (V. 1 1)
                      ::speed     (V. 1 1)
                      ::energy    0.8
                      ::invisible 0.7}]
   ::auto-hunters   [{::id        100
                      ::position  (V. 133 34)
                      ::speed     (V. -1 2)
                      ::energy    0.8
                      ::invisible 0.7}]
   ::prays          [{::id       11
                      ::position (V. 2 2)
                      ::speed    (V. 2 1)
                      ::energy   0.8
                      ::poisoned 0.6}
                     {::id       12
                      ::position (V. 199 99)
                      ::speed    (V. 6 3)
                      ::energy   0.8
                      ::poisoned 0.4}
                     {::id       12
                      ::position (V. 199 99)
                      ::speed    (V. 6 3)
                      ::energy   0.8
                      ::poisoned 0.4}
                     {::id       12
                      ::position (V. 199 99)
                      ::speed    (V. 6 3)
                      ::energy   0.8
                      ::poisoned 0.4}
                     {::id       12
                      ::position (V. 199 99)
                      ::speed    (V. 6 3)
                      ::energy   0.8
                      ::poisoned 0.4}
                     {::id       12
                      ::position (V. 199 99)
                      ::speed    (V. 6 3)
                      ::energy   0.8
                      ::poisoned 0.4}
                     {::id       12
                      ::position (V. 199 99)
                      ::speed    (V. 6 3)
                      ::energy   0.8
                      ::poisoned 0.4}
                     {::id       12
                      ::position (V. 199 99)
                      ::speed    (V. 6 3)
                      ::energy   0.8
                      ::poisoned 0.4}]
   ::game-size      (V. 600 400)})

(comment
  (s/explain ::moving-object {::id       12
                              ::position (V. 12 23)
                              ::speed    (V. 12 2)
                              ::energy   0.8})
  (s/explain ::world
             {::player-hunters [{::id        12
                                 ::position  (V. 12 23)
                                 ::speed     (V. 12 2)
                                 ::energy    0.8
                                 ::invisible 0.7}]
              ::prays          [{::id       12
                                 ::position (V. 12 23)
                                 ::speed    (V. 12 2)
                                 ::energy   0.8
                                 ::poisoned 0.6}
                                {::id       12
                                 ::position (V. 12 23)
                                 ::speed    (V. 12 2)
                                 ::energy   0.8
                                 ::poisoned 0.4}]
              ::game-size      (V. 200 100)}))

;; example of init data

#_(def example-init
    {:player-hunters 2
     :auto-hunters   1
     :prays          4
     :game-size      (V. 1500 700)})


#_(defn initial-example-model
    "model to start with - e.g. for testing and for playing a very simple test game"
    [init-map]
    (let [game-size      (:game-size init-map)
          num-p-hunters  (:player-hunters init-map)
          num-a-hunters  (:auto-hunters init-map)
          num-prays      (:prays init-map)
          hunter-fn      (fn [id-start num]
                           (mapv
                             (fn [idx] {::id        (+ id-start idx)
                                        ::position  (V. idx idx)
                                        ::speed     (V. idx idx)
                                        ::invisible 0.5})
                             (range id-start (+ id-start num))))

          player-hunters (hunter-fn 1 num-p-hunters)
          auto-hunters   (hunter-fn 100 num-a-hunters)

          prays          (mapv (fn [num] {::id       num
                                          ::position (v/v-minus game-size (V. num num))
                                          ::speed    (V. (- num) (- num))
                                          ::poisoned 0.1})
                               (range 1000 (+ 1000 num-prays)))]
      {::player-hunters player-hunters
       ::auto-hunters   auto-hunters
       ::prays          prays
       ::game-size      (:game-size init-map)}))
