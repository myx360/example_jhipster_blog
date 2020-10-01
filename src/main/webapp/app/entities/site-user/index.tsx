import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SiteUser from './site-user';
import SiteUserDetail from './site-user-detail';
import SiteUserUpdate from './site-user-update';
import SiteUserDeleteDialog from './site-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SiteUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SiteUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SiteUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={SiteUser} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SiteUserDeleteDialog} />
  </>
);

export default Routes;
