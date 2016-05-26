(ns hello-world.core
  (:require
   [clj-http.client :as client]
   [cheshire.core :refer :all])
  (:gen-class))

(defn get-data []
  (let [url "https://randomuser.me/api"
        body (:body (client/get url))]
      (parse-string body true)))

(defn userdata [data]
  (first (:results data)))

(defn create-people []
  (repeatedly 10 #(userdata (get-data))))

(defn prepare-data []
  (let [persons (create-people)
        all-genders (map :gender persons)]
    (assoc
      {}
      :data persons
      :genders (frequencies all-genders))))


(defn -main
  [& args]
  (spit "output.txt" (generate-string (prepare-data))))
