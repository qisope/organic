(ns organic.physics.segments
  (:require [organic.plants.parameters :as parameters]))

(defn segment
  ([plant-params node1 node2 index]
   (let [thickness (parameters/get-scaled-plant-parameter
                     plant-params
                     :root-thickness
                     :tip-thickness
                     index
                     (:segment-count plant-params))]
     {:index index
      :length (:segment-length plant-params)
      :thickness thickness
      :node1 node1
      :node2 node2
      :spring nil})))
