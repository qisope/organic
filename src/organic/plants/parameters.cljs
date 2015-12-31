(ns organic.plants.parameters
  (:require [organic.physics.vectors :as vectors])
  )

(def world-parameters {
                :gravity (vectors/vector2d 0 -10)
                :wind (vectors/vector2d 2 0)
                })

(def world-random-limits {
                          :plant-count {:min 1 :max 1}
                          })

(def plant-config {:segment-interval 0.5})

(def plant-random-limits {
                :growth-rate {:min 0.1 :max 5}
                :height {:min 5 :max 10}
                :root-thickness {:min 0.1 :max 0.3}
                :tip-thickness {:min 0.01 :max 0.05}
                :root-node-weight {:min 1 :max 2}
                :tip-node-weight {:min 0.1 :max 0.5}
                :curvyness {:min 0 :max 1}
                :branchyness {:min 0 :max 1}
                :branch-levels {:min 0 :max 2}
                })

(defn transform-map [map func]
  (into {} (for [[key value] map] [key (func value)])))

(defn get-random-parameter [limits]
  (let [min (:min limits), max (:max limits)]
    (+ min (* (- max min) (.random js/Math)))))

(defn get-scaled-value [value1 value2 index index-max]
  (+ value1 (* index (/ (- value2 value1) index-max))))

(defn get-scaled-plant-parameter [plant-params key1 key2 index index-max]
  (let [value1 (key1 plant-params), value2 (key2 plant-params)]
    (get-scaled-value value1 value2 index index-max)))

(defn get-segment-count [plant-params]
  (.ceil js/Math (/ (:height plant-params) (:segment-interval plant-config))))

(defn get-segment-length [plant-params, segment-count]
  (/ (:height plant-params segment-count)))

(defn initialize-plant-parameters []
  (let [plant-params (transform-map plant-random-limits get-random-parameter)]
    (let [segment-count (get-segment-count plant-params)]
      (let [segment-length (get-segment-length plant-params segment-count)]
        (into plant-params {:segment-count segment-count
                            :segment-length segment-length
                            :node-count (inc segment-count)})))))

(defn initialize-world-parameters []
  (into world-parameters
        (transform-map world-random-limits get-random-parameter)))
