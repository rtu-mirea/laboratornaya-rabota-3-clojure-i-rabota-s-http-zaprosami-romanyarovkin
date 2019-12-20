(ns metrics-server.api.files
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-files-with-http-info
  "Get files in directory on server"
  []
  (call-api "/files" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-files
  "Get files in directory on server"
  []
  (:data (get-files-with-http-info)))

(defn task21 [files]
  (filter (fn [x] (not (get x :directory))) files)
  )
(defn task22 [files]
  (filter (fn [x] (get x :executable)) files)
  )
(defn replaceExpansion [filename]
  (clojure.string/replace filename #".conf" ".cfg")
  )
(defn task23 [files]
  (map (fn [file]
        {
         :name (replaceExpansion (get file :name))
         :size (get file :size)
         :changed (get file :changed)
         :directory (get file :directory)
         :executable (get file :executable)
         }
        ) files)
  )
(defn task24 [files]
  (/
    (reduce + (map (fn [file] (get file :size)) (filter (fn [x] (not (get x :directory))) files)))
    (count (filter (fn [x] (not (get x :directory))) files))
    )
  )
(defn -main [& args]
  (println (task21 (get-files)))
  (println (task22 (get-files)))
  (println (task23 (get-files)))
  (println (task24 (get-files)))
  )
