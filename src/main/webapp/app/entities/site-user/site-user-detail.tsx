import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './site-user.reducer';
import { ISiteUser } from 'app/shared/model/site-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISiteUserDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SiteUserDetail = (props: ISiteUserDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { siteUserEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          SiteUser [<b>{siteUserEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="displayName">Display Name</span>
          </dt>
          <dd>{siteUserEntity.displayName}</dd>
          <dt>
            <span id="moderator">Moderator</span>
          </dt>
          <dd>{siteUserEntity.moderator ? 'true' : 'false'}</dd>
          <dt>User</dt>
          <dd>{siteUserEntity.userId ? siteUserEntity.userId : ''}</dd>
        </dl>
        <Button tag={Link} to="/site-user" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/site-user/${siteUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ siteUser }: IRootState) => ({
  siteUserEntity: siteUser.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SiteUserDetail);
