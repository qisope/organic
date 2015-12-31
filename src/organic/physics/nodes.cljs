(ns organic.physics.nodes
(:require [organic.physics.points :as points]
          [organic.physics.vectors :as vectors]
          ))

(defn node
  ([mass position velocity force]
    {
     :mass mass
     :position position
     :velocity velocity
     :force force
     :fixed false})
  ([mass position velocity]
    (node mass position velocity (vectors/empty)))
  ([mass position]
    (node mass position (vectors/empty) (vectors/empty)))
  ([mass]
    (node mass (points/empty) (vectors/empty) (vectors/empty))))

(defn make-fixed
  ([node fixed]
   (assoc node :fixed fixed))
  ([node]
   (assoc node :fixed true)))
