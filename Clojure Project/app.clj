(ns app
  (:require [a3.db :as db]
            [a3.menu :as menu]))

(defn -main []
  (let [cities-db (db/load-cities "cities.txt")]
    (menu/menu cities-db)))

(-main)
