(defproject lein-electron "0.1.0-SNAPSHOT"
  :license {:name "The MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :source-paths ["src"]
  :description "cljs-electron-start"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [figwheel "0.5.14"]
                 [org.omcljs/om "1.0.0-beta1"]
                 [ring/ring-core "1.6.3"]]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.14"]
            [lein-cooper "1.2.2"]]

  :clean-targets ^{:protect false} ["resources/main.js"
                                    "resources/public/js/app.js"
                                    "resources/public/js/app.js.map"
                                    "resources/public/js/app"]
  :cljsbuild
  {:builds
   [{:source-paths ["src/electron"]
     :id "electron-dev"
     :compiler {:output-to "resources/main.js"
                :output-dir "resources/public/js/electron-dev"
                :optimizations :simple
                :pretty-print true
                :cache-analysis true}}
    {:source-paths ["src/app" "src/dev"]
     :id "app-dev"
     :compiler {:output-to "resources/public/js/app.js"
                :output-dir "resources/public/js/app"
                :source-map true
                :asset-path "js/app"
                :optimizations :none
                :cache-analysis true
                :main "dev.core"}}

    {:source-paths ["src/electron"]
     :id "electron-release"
     :compiler {:output-to "resources/main.js"
                :output-dir "resources/public/js/electron-release"
                :optimizations :advanced
                :pretty-print true
                :cache-analysis true
                :infer-externs true}}
    {:source-paths ["src/app"]
     :id "app-release"
     :compiler {:output-to "resources/public/js/app.js"
                :output-dir "resources/public/js/app-release"
                :source-map "resources/public/js/app.js.map"
                :optimizations :advanced
                :cache-analysis true
                :infer-externs true
                :main "app.core"}}]}

  :figwheel {:http-server-root "public"
             :css-dirs ["resources/public/css"]
             :ring-handler tools.figwheel-middleware/app
             :server-port 3449})
