var path = require('path');
var webpack = require('webpack');
module.exports = {
    // entry: ['babel-polyfill','./src/main/js/index.tsx'],
    entry: {
        bundle : './src/main/js/index.tsx',
        // fxContainer: './src/main/js/container/finantix/FxContainer.tsx',
        // store: './src/main/js/store/index.ts'
    },
    devtool: 'sourcemaps',
    cache: true,
    output: {
        path: __dirname + '/src/main/resources/static/built',
        // filename: './src/main/resources/static/built/bundle.js',
        filename: '[name].js',
        libraryTarget: 'var',
        // `library` determines the name of the global variable
        library: '[name]'
    },
    devtool: "source-map",

    resolve: {
        // Add '.ts' and '.tsx' as resolvable extensions.
        extensions: [".ts", ".tsx", ".js", ".json"]
    },

    module: {
        rules: [
            // All files with a '.ts' or '.tsx' extension will be handled by 'awesome-typescript-loader'.
            { test: /\.tsx?$/, loader: "awesome-typescript-loader" },
            { enforce: "pre", test: /\.js$/, loader: "source-map-loader" },
            // {
            //     test: path.join(__dirname, '.'),
            //     exclude: /(node_modules)/,
            //     loader: 'babel-loader',
            //     query: {
            //         cacheDirectory: true,
            //         presets: ['env', 'react', 'flow']
            //     }
            // },
            {
                test: /\.svg$/,
                loader: 'babel-loader!svg-react-loader'
            },
            {test: /\.css$/, include: path.resolve(__dirname, 'src/main/js/'), loader: "style-loader!typings-for-css-modules-loader?modules&namedExport&localIdentName='[name]__[local]___[hash:base64:5]'"},
            {test: /\.css$/, exclude: path.resolve(__dirname, 'src/main/js/'), loader: "style-loader!css-loader"},
            {
                test: /\.less$/,
                include:  path.resolve(__dirname, 'src/main/js/'),
                use: [{
                    loader: "style-loader" // creates style nodes from JS strings
                }, {
                    loader: "css-loader" // translates CSS into CommonJS
                }, {
                    loader: "less-loader", options: {
                        strictMath: true,
                    }
                }]
            },
            // {test: /\.css$/, loader: "style-loader!typings-for-css-modules-loader?modules&namedExport&localIdentName='[name]__[local]___[hash:base64:5]'"}
            // {
            //     test: /\.css$/,
            //     include: path.join(__dirname, 'src/js/'),
            //     use: [
            //         'style-loader',
            //         {
            //             loader: 'typings-for-css-modules-loader',
            //             options: {
            //                 modules: true,
            //                 namedExport: true
            //             }
            //         }
            //     ]
            // }
        ]
    },

    plugins: [
        new webpack.WatchIgnorePlugin([
            /css\.d\.ts$/
        ]),
    ]
    // externals: {
    //     "react": "React",
    //     "react-dom": "ReactDOM"
    // }
};