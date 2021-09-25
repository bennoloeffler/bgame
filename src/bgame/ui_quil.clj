(ns bgame.ui-quil
  (:require [bgame.model :as model]
            [bgame.engine :as engine]
            [quil.core :as q]
            [quil.middleware :as m]
            [clojure.core.async :refer [ chan >!! <!!]])
  (:import (clojure.core.async.impl.channels ManyToManyChannel)))

(defn setup [config]
  ; Set frame rate to 30 frames per second.
  (println config)
  (q/frame-rate 100)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :rgb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  #_{:color 0
     :angle 0}
  ;model/initial-model
  (let [c (engine/run model/initial-model)]
    [c (<!! c)]))


(defn update-state [[c first-state-to-ignore]]
  ; Update sketch state by changing circle color and position.
  #_{:color (mod (+ (:color state) 0.7) 255)
     :angle (+ (:angle state) 0.1)}
  ;(first (engine/next-step [state {}])))
  [c (<!! c)])

(defn draw-point [size x y]
  (q/ellipse x y size size))

(defn draw-points [points [r g b] size]
  (q/fill r g b)
  ;(println "r:" r " g:" g " b:" b)
  (loop [to-draw points]
    (when (seq to-draw)
      (let [o (first to-draw)
            r (rest to-draw)]
         (do (draw-point size (-> o ::model/position :x) (-> o ::model/position :y))
             (recur r))))))


(defn draw-state [[c state]]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  ; Set circle color.
  (draw-points (state ::model/prays) [100 200 10] 10)
  (draw-points (state ::model/player-hunters) [0 200 250] 40)
  (draw-points (state ::model/auto-hunters) [204 102 0] 20))



(defn stop [app] (-> @app .dispose))

(defn start [config]
  (println config)
  (q/defsketch b-game
               :title "bgame"
               :size [(-> model/initial-model ::model/game-size :x) (-> model/initial-model ::model/game-size :y)]
               ; setup function called only once, during sketch initialization.
               :setup (partial setup config)
               ; update-state is called on each iteration before draw-state.
               :update update-state
               :draw draw-state

               :features [:keep-on-top :resizable]
               ; This sketch uses functional-mode middleware.
               ; Check quil wiki for more info about middlewares and particularly
               ; fun-mode.
               :middleware [m/fun-mode]))
