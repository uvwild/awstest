# source of the data collecting distributed system

## a distributed client which sends computed datapackets to the backend
  
### a local multithreaded implementation will suffice at the start

#### (c) 2016 UV

### do we need those


    "grunt-contrib-compass": "~0.6.0",
    "grunt-contrib-copy": "~0.7.0",
    "grunt-contrib-jshint": "~0.6.3",
    "grunt-contrib-uglify": "~0.7.0",

    "build-css": "catw -c 'lessc -' 'style/*.less' > static/bundle.css",


  "scripts": {
    "prebuild": "npm install",
    "watch-css": "catw -c 'lessc -' 'style/*.less' -o static/bundle.css -v",
    "watch-js": "watchify browser/*.js -o static/bundle.js -dv",
    "watch": "npm run watch-css & npm run watch-js",
    "build-js": "browserify browser/*.js > static/bundle.js",
    "build": "npm run build-css && npm run build-js",
    "test": "echo \"Error: no test specified\" && exit 1"
  },



  "dependencies": {
    "css-loader": "^0.23.1",
    "file-loader": "^0.9.0",
    "font-loader": "^0.1.2",
    "html-loader": "^0.4.3",
    "jshint": "^2.9.2",
    "jshint-loader": "^0.8.3",
    "json-loader": "^0.5.4",
    "less-loader": "^2.2.3",
    "style-loader": "^0.13.1",
    "svg-loader": "0.0.2",
    "transform-loader": "^0.2.3",
    "url-loader": "^0.5.7"
  }
