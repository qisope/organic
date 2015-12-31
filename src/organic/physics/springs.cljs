(ns organic.physics.springs)

(defn spring [node1 node2 stiffness damping]
  {:node1 node1 :node2 node2 :stiffness stiffness :damping damping})

(defn angular-spring [strut1 strut2 stiffness damping]
  {:strut1 strut1 :strut2 strut2 :stiffness stiffness :damping damping})
