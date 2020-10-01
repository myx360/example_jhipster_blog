import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBlog } from 'app/shared/model/blog.model';
import { getEntities as getBlogs } from 'app/entities/blog/blog.reducer';
import { getEntity, updateEntity, createEntity, reset } from './post.reducer';
import { IPost } from 'app/shared/model/post.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPostUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PostUpdate = (props: IPostUpdateProps) => {
  const [blogId, setBlogId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { postEntity, blogs, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/post' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getBlogs();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...postEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lecgApp.post.home.createOrEditLabel">Create or edit a Post</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : postEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="post-id">ID</Label>
                  <AvInput id="post-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="post-title">
                  Title
                </Label>
                <AvField
                  id="post-title"
                  type="text"
                  name="title"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateLabel" for="post-date">
                  Date
                </Label>
                <AvField
                  id="post-date"
                  type="date"
                  className="form-control"
                  name="date"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="post-type">
                  Type
                </Label>
                <AvInput id="post-type" type="select" className="form-control" name="type" value={(!isNew && postEntity.type) || 'NEWS'}>
                  <option value="NEWS">NEWS</option>
                  <option value="BLOG">BLOG</option>
                  <option value="EVENT">EVENT</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="contentLabel" for="post-content">
                  Content
                </Label>
                <AvField
                  id="post-content"
                  type="text"
                  name="content"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="pinnedLabel">
                  <AvInput id="post-pinned" type="checkbox" className="form-check-input" name="pinned" />
                  Pinned
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="eventTimeLabel" for="post-eventTime">
                  Event Time
                </Label>
                <AvField id="post-eventTime" type="date" className="form-control" name="eventTime" />
              </AvGroup>
              <AvGroup>
                <Label for="post-blog">Blog</Label>
                <AvInput id="post-blog" type="select" className="form-control" name="blogId">
                  <option value="" key="0" />
                  {blogs
                    ? blogs.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/post" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  blogs: storeState.blog.entities,
  postEntity: storeState.post.entity,
  loading: storeState.post.loading,
  updating: storeState.post.updating,
  updateSuccess: storeState.post.updateSuccess,
});

const mapDispatchToProps = {
  getBlogs,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PostUpdate);
