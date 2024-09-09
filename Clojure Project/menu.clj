(ns a3
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def cities-file "cities.txt")

(defn load-cities []
  (with-open [reader (io/reader cities-file)]
    (doall
      (map #(zipmap [:name :province :size :population :area] 
                    (map str/trim (str/split % #"\|"))) 
           (line-seq reader)))))

(def citiesDB (load-cities)) 

(defn display-city-info [cities city-name]
  (let [city (some #(when (= (:name %) city-name) %) cities)]
    (if city
      (println (format "City: %s, Province: %s, Size: %s, Population: %s, Area: %s"
                       (:name city) (:province city) (:size city) (:population city) (:area city)))
      (println (format "City %s not found" city-name)))))

(defn list-all-cities [cities]
  (->> cities
       (sort-by :name)
       (map #(str (:name %)))))

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
        provinces (map (fn [province-cities]
                         (let [province (key province-cities)
                               count (count (val province-cities))]
                           [province count]))
                       grouped-by-province)
        sorted-provinces (sort-by second > provinces)
        total-provinces (count grouped-by-province)
        total-cities (count cities)]
    (concat (map-indexed (fn [index province-count]
                           (let [province (first province-count)
                                 count (second province-count)]
                             [(inc index) province count]))
                         sorted-provinces)
            [["Total provinces:" total-provinces "Total cities:" total-cities]])))

(defn list-provinces-with-population [cities]
  (let [grouped-by-province (group-by :province cities)
        provinces (map (fn [[province cities]]
                         (let [total-population (apply + (map #(read-string (:population %)) cities))]
                           [province total-population]))
                       grouped-by-province)
        sorted-provinces (sort-by first provinces)]
    (map-indexed #(vector (inc %) (first %2) (second %2)) sorted-provinces)))


(defn show-menu []
  (println "\n\n*** City Information Menu ***")
  (println "-----------------------------\n")
  (println "1. List Cities")
  (println "2. Display City Information")
  (println "3. List Provinces")
  (println "4. Display Province Information")
  (println "5. Exit")
  (print "\nEnter an option? ")
  (flush)
  (read-line))

(defn process-option1 [cities sub-option]
  (case sub-option
    "1" (println (list-all-cities cities))
    "2" (do (print "\nEnter province name => ")
            (flush)
            (let [province (read-line)]
              (println (list-cities-by-province cities province))))
    "3" (do (print "\nEnter province name => ")
            (flush)
            (let [province (read-line)]
              (println (list-cities-by-density cities province))))
    (println "Invalid sub-option, please try again")))

(defn process-option2 [cities]
  (print "\nPlease enter the city name => ")
  (flush)
  (let [city-name (read-line)]
    (display-city-info cities city-name)))

(defn process-option3 [cities]
  (doseq [province (list-all-provinces cities)]
    (println province)))

(defn process-option4 [cities]
  (doseq [[index province population] (list-provinces-with-population cities)]
    (println (format "%d. %s - Total Population: %d" index province population))))

(defn process-option [cities option]
  (case option
    "1" (do (println "\n1.1. List all cities, ordered by city name (ascending)")
             (println "1.2. List all cities for a given province, ordered by size (descending) and name (ascending)")
             (println "1.3. List all cities for a given province, ordered by population density in ascending order")
             (print "\nEnter sub-option: ")
             (flush)
             (let [sub-option (read-line)]
               (process-option1 cities sub-option)))
    "2" (process-option2 cities)
    "3" (process-option3 cities)
    "4" (process-option4 cities)
    "5" (println "\nGoodBye\n")
    (println "Invalid Option, please try again")))

(defn menu []
  (loop []
    (let [option (str/trim (show-menu))]
      (if (= option "5")
        (println "\nGoodBye\n")
        (do
          (process-option citiesDB option)
          (recur))))))


(menu)
