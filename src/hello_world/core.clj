(ns hello-world.core
  (:require
   [clj-http.client :as client]
   [cheshire.core :refer :all])
  (:gen-class))

(defn get-data []
  (let [url "https://randomuser.me/api"]
      (parse-string
        (:body (client/get url)) true)))

(defn userdata [data]
  (first (:results data)))

(defn user-gender [data]
  (:gender data))

(defn all-genders [data]
  (map user-gender data))

(defn count-genders [genders]
  (frequencies genders))

(defn people []
  (repeatedly 10 #(userdata (get-data))))

(defn prepare-data []
  (let [persons (people)]
    (assoc
      {}
      :data persons
      :genders (count-genders (all-genders persons)))))

(defn to-json []
  (generate-string (prepare-data)))

(defn write-data []
  (spit "output.txt" (to-json)))

(defn -main
  [& args]
  (write-data))