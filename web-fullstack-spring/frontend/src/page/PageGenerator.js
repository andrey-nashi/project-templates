import React, {Component} from 'react'
import {Route, Switch} from 'react-router-dom'

import MenuBar from '../cmp/MenuBar'

import PageLogout from './PageLogout'
import PageLogin from './PageLogin'
import PageHome from './PageHome'
import UsrPanel from './UsrPanel'
import AuthenticatedRoute from '../auth/AuthenticatedRoute'

class PageGenerator extends Component {

    render() {
        return (
          <div>
            <MenuBar/>

            <Switch>
                <Route path="/" exact component={PageHome}/>
                <Route path="/home" exact component={PageHome}/>
                <Route path="/login" exact component={PageLogin}/>
                <Route path="/logout" exact component={PageLogout}/>
                <AuthenticatedRoute path="/user-panel" exact component={() => <UsrPanel location="main"/>}/>
                <AuthenticatedRoute path="/collection" exact component={() => <UsrPanel location="collection"/>}/>
                <AuthenticatedRoute path="/upload" exact component={() => <UsrPanel location="upload"/>}/>
            </Switch>
          </div>
        )
    }
}

export default PageGenerator;