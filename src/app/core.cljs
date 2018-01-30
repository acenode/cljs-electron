(ns app.core
  (:require [goog.dom :as gdom]
            [om.dom :as dom]
            [om.next :as om :refer [defui]]
            [clojure.string :as string :refer [split-lines]]))

(def join-lines (partial string/join "\n"))

(enable-console-print!)

(defonce state        (atom 0))
(defonce shell-result (atom ""))
(defonce command      (atom ""))

(defonce proc (js/require "child_process"))

(defn append-to-out [out]
  (swap! shell-result str out))

(defn run-process []
  (when-not (empty? @command)
    (println "Running command" @command)
    (let [[cmd & args] (string/split @command #"\s")
          js-args (clj->js (or args []))
          p (.spawn proc cmd js-args)]
      (.on p "error" (comp append-to-out
                           #(str % "\n")))
      (.on (.-stderr p) "data" append-to-out)
      (.on (.-stdout p) "data" append-to-out))
    (reset! command "")))

(defui Hello
  Object
  (render [this]
    (dom/h1 nil "Hello, world!")))
(def hello (om/factory Hello))

(.render js/ReactDOM (hello) (gdom/getElement "app-container"))