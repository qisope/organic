(ns organic.core
  (:require [organic.plants.parameters :as parameters]
            [organic.physics.points :as points]
            [organic.physics.vectors :as vectors]
            [organic.physics.nodes :as nodes]
            [organic.physics.segments :as segments]
            ))

(enable-console-print!)

(defn create-root-node [plant-params]
  (let [node (nodes/node (:root-node-weight plant-params))]
    (nodes/make-fixed node)))

(defn create-plant-node [plant-params index]
  (let [weight (parameters/get-scaled-plant-parameter
                 plant-params
                 :root-node-weight
                 :tip-node-weight
                 index
                 (:node-count plant-params)
                 )]
    (nodes/node weight)))

(defn create-segment [plant-params node1 node2 index]
  (segments/segment plant-params node1 node2 index))

(defn create-segments [plant-params]
  (let [root-node (create-root-node plant-params)]
   (loop [node1 root-node, index 0, segments []]
    (if (< index (:segment-count plant-params))
      (let [node2 (create-plant-node plant-params (inc index))]
        (let [segment (create-segment plant-params node1 node2 index)]
         (recur node2 (inc index) (conj segments segment))))
      segments))))

(defn create-plant []
  (let [plant-params (parameters/initialize-plant-parameters)]
    (let [segments (create-segments plant-params)]
      {:segments segments})))

(defn create-plants [world-params]
  (vec (repeatedly (:plant-count world-params) create-plant)))

(defn create-world []
  (let [world-params (parameters/initialize-world-parameters)]
    {:world-params world-params
     :plants (create-plants world-params)}))

(defonce app-state (atom (create-world)))

(defn update-state [f k]
  (let [new-value (f (k @app-state))]
    (swap! app-state assoc-in [k] new-value)))

(defn update-plants [f]
  (update-state f :plants))

(println (get-in @app-state [:plants 0 :segments 0 :thickness]))

(update-plants #(assoc-in % [0 :segments 0 :thickness] 99))

(println (get-in @app-state [:plants 0 :segments 0 :thickness]))

(defn on-js-reload []
  (swap! app-state create-world)
)
