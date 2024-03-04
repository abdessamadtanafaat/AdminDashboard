import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import 'react-toastify/dist/ReactToastify.css';
import { Provider } from 'react-redux';
import {store} from './app/store'
import {ToastContainer} from 'react-toastify'


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Provider store={store}>
    <App />
    <ToastContainer position ="top-center" theme="colored"/>
    </Provider>
    
  </React.StrictMode>
);

