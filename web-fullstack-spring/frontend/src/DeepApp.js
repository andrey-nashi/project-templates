import React, {Component} from 'react'
import {BrowserRouter, Route, Switch} from 'react-router-dom'
import 'semantic-ui-css/semantic.min.css'

import PageGenerator from './page/PageGenerator'
//---------------------------------------------------------
//---- This is the main class of the application
//---------------------------------------------------------
class DeepApp extends Component {

  render() {
      return(
        <BrowserRouter>
            <PageGenerator />
        </BrowserRouter>

      )
  }
}


export default DeepApp;
