const CopyWebpackPlugin = require('copy-webpack-plugin');
const path = require('path');
const Dotenv = require('dotenv-webpack');
var DOT_ENV_PATH = './.env';
/*
if(process.env.CONTAINER_ENV == 'true') {
    DOT_ENV_PATH='/opt/token-inspector/env.production';
}
*/

module.exports = env => ({
  entry: './src/index.js',
  output: {
    filename: 'main.js',
    path: path.resolve(__dirname, 'build'),
  },
  plugins: [
    new CopyWebpackPlugin({
      patterns: [
        { from: 'public' }
      ]
    }),
    new Dotenv({
        path: `${DOT_ENV_PATH}`
    })
  ]
});
