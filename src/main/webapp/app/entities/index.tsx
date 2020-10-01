import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SiteUser from './site-user';
import Blog from './blog';
import Post from './post';
import Subject from './subject';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}site-user`} component={SiteUser} />
      <ErrorBoundaryRoute path={`${match.url}blog`} component={Blog} />
      <ErrorBoundaryRoute path={`${match.url}post`} component={Post} />
      <ErrorBoundaryRoute path={`${match.url}subject`} component={Subject} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
