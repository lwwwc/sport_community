importScripts('https://www.gstatic.com/firebasejs/8.2.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.2.2/firebase-messaging.js');

firebase.initializeApp({
  apiKey: "AIzaSyBPEuSspyHhkgI66Tc6dYzqlScBPryAX8Q",
  authDomain: "sportcommunity-f94c9.firebaseapp.com",
  projectId: "sportcommunity-f94c9",
  storageBucket: "sportcommunity-f94c9.appspot.com",
  messagingSenderId: "508688696024",
  appId: "1:508688696024:web:f46882d735d9086e430dad"
});

const messaging = firebase.messaging();
