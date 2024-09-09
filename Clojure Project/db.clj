(ns a3.db
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn load-cities [file]
  (with-open [reader (io/reader file)]
    (doall
      (map #(zipmap [:name :province :size :population :area]
                    (map str/trim (str/split % #"\|")))
           (line-seq reader)))))

(defn display-city-info [cities city-name]
  (let [city (some #(when (= (:name %) city-name) %) cities)]
    (if city
      (println (format "City: %s, Province: %s, Size: %s, Population: %s, Area: %s"
                       (:name city) (:province city) (:size city) (:population city) (:area city)))
      (println (format "City %s not found" city-name)))))

(defn list-cities-by-province [cities province]
  (let [filtered-cities (filter #(= (:province %) province) cities)
        size-order {"Small" 1 "Medium" 2 "Large urban" 3}]
    (->> filtered-cities
         (sort-by (juxt (comp - size-order :size) :name))
         (map #(vector (:name %) (:size %) (:population %))))))

(defn list-cities-by-density [cities province]
  (let [filtered-cities (filter #(= (:province %) province) cities)]
    (->> filtered-cities
         (sort-by (fn [city] (/ (read-string (:population city)) (read-string (:area city)))))
         (map #(vector (:name %) (:province %) (:population %))))))

(defn list-all-provinces [cities]
  (let [grouped-by-province (group-by :province cities)
        provinces (map #(vector (key %) (count (val %))) grouped-by-province)
        sorted-provinces (sort-by second > provinces)
        total-provinces (count grouped-by-province)
        total-cities (count cities)]
    (concat (map-indexed #(vector (inc %) (first %) (second %)) sorted-provinces)
            [["Total provinces:" total-provinces "Total cities:" total-cities]])))

(defn list-provinces-with-population [cities]
  (let [grouped-by-province (group-by :province cities)
        provinces (map (fn [province-data]
                         (let [province (key province-data)
                               total-population (apply + (map #(read-string (:population %)) (val province-data)))]
                           [province total-population]))
                       grouped-by-province)
        sorted-provinces (sort-by first provinces)]
    (map-indexed #(vector (inc %) (first %) (second %)) sorted-provinces)))
