import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IReworkDetail } from 'app/shared/model/rework-detail.model';
import { getEntities as getReworkDetails } from 'app/entities/rework-detail/rework-detail.reducer';
import { IReworkComment } from 'app/shared/model/rework-comment.model';
import { getEntity, updateEntity, createEntity, reset } from './rework-comment.reducer';

export const ReworkCommentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const reworkDetails = useAppSelector(state => state.reworkDetail.entities);
  const reworkCommentEntity = useAppSelector(state => state.reworkComment.entity);
  const loading = useAppSelector(state => state.reworkComment.loading);
  const updating = useAppSelector(state => state.reworkComment.updating);
  const updateSuccess = useAppSelector(state => state.reworkComment.updateSuccess);

  const handleClose = () => {
    navigate('/rework-comment' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getReworkDetails({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...reworkCommentEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      reworkDetail: reworkDetails.find(it => it.id.toString() === values.reworkDetail.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdAt: displayDefaultDateTime(),
        }
      : {
          ...reworkCommentEntity,
          createdAt: convertDateTimeFromServer(reworkCommentEntity.createdAt),
          user: reworkCommentEntity?.user?.id,
          reworkDetail: reworkCommentEntity?.reworkDetail?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmicaApp.reworkComment.home.createOrEditLabel" data-cy="ReworkCommentCreateUpdateHeading">
            <Translate contentKey="farmicaApp.reworkComment.home.createOrEditLabel">Create or edit a ReworkComment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="rework-comment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmicaApp.reworkComment.comment')}
                id="rework-comment-comment"
                name="comment"
                data-cy="comment"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmicaApp.reworkComment.createdAt')}
                id="rework-comment-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="rework-comment-user"
                name="user"
                data-cy="user"
                label={translate('farmicaApp.reworkComment.user')}
                type="select"
                required
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="rework-comment-reworkDetail"
                name="reworkDetail"
                data-cy="reworkDetail"
                label={translate('farmicaApp.reworkComment.reworkDetail')}
                type="select"
                required
              >
                <option value="" key="0" />
                {reworkDetails
                  ? reworkDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rework-comment" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ReworkCommentUpdate;
