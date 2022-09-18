import React from 'react';
import ReactDOM from 'react-dom';
import DeepApp from './DeepApp';
import * as serviceWorker from './serviceWorker';

ReactDOM.render(<DeepApp />, document.getElementById('app'));

serviceWorker.unregister();
