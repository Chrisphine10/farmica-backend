import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWarehouseDetail } from 'app/shared/model/warehouse-detail.model';
import { getEntities as getWarehouseDetails } from 'app/entities/warehouse-detail/warehouse-detail.reducer';
import { ILotDetail } from 'app/shared/model/lot-detail.model';
import { getEntities as getLotDetails } from 'app/entities/lot-detail/lot-detail.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IReworkDetail } from 'app/shared/model/rework-detail.model';
import { ReworkStatus } from 'app/shared/model/enumerations/rework-status.model';
import { getEntity, updateEntity, createEntity, reset } from './rework-detail.reducer';

export const ReworkDetailUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const warehouseDetails = useAppSelector(state => state.warehouseDetail.entities);
  const lotDetails = useAppSelector(state => state.lotDetail.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const reworkDetailEntity = useAppSelector(state => state.reworkDetail.entity);
  const loading = useAppSelector(state => state.reworkDetail.loading);
  const updating = useAppSelector(state => state.reworkDetail.updating);
  const updateSuccess = useAppSelector(state => state.reworkDetail.updateSuccess);
  const reworkStatusValues = Object.keys(ReworkStatus);

  const handleClose = () => {
    navigate('/rework-detail');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWarehouseDetails({}));
    dispatch(getLotDetails({}));
    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...reworkDetailEntity,
      ...values,
      warehouseDetail: warehouseDetails.find(it => it.id.toString() === values.warehouseDetail.toString()),
      lotDetail: lotDetails.find(it => it.id.toString() === values.lotDetail.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          status: 'PENDING',
          ...reworkDetailEntity,
          createdAt: convertDateTimeFromServer(reworkDetailEntity.createdAt),
          warehouseDetail: reworkDetailEntity?.warehouseDetail?.id,
          lotDetail: reworkDetailEntity?.lotDetail?.id,
          user: reworkDetailEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmicaApp.reworkDetail.home.createOrEditLabel" data-cy="ReworkDetailCreateUpdateHeading">
            <Translate contentKey="farmicaApp.reworkDetail.home.createOrEditLabel">Create or edit a ReworkDetail</Translate>
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
                  id="rework-detail-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmicaApp.reworkDetail.uicode')}
                id="rework-detail-uicode"
                name="uicode"
                data-cy="uicode"
                type="text"
                validate={{}}
              />
              <ValidatedField
                label={translate('farmicaApp.reworkDetail.pdnDate')}
                id="rework-detail-pdnDate"
                name="pdnDate"
                data-cy="pdnDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmicaApp.reworkDetail.reworkDate')}
                id="rework-detail-reworkDate"
                name="reworkDate"
                data-cy="reworkDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmicaApp.reworkDetail.numberOfCTNs')}
                id="rework-detail-numberOfCTNs"
                name="numberOfCTNs"
                data-cy="numberOfCTNs"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('farmicaApp.reworkDetail.startCTNNumber')}
                id="rework-detail-startCTNNumber"
                name="startCTNNumber"
                data-cy="startCTNNumber"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.reworkDetail.endCTNNumber')}
                id="rework-detail-endCTNNumber"
                name="endCTNNumber"
                data-cy="endCTNNumber"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.reworkDetail.status')}
                id="rework-detail-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {reworkStatusValues.map(reworkStatus => (
                  <option value={reworkStatus} key={reworkStatus}>
                    {translate('farmicaApp.ReworkStatus.' + reworkStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('farmicaApp.reworkDetail.createdAt')}
                id="rework-detail-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="rework-detail-warehouseDetail"
                name="warehouseDetail"
                data-cy="warehouseDetail"
                label={translate('farmicaApp.reworkDetail.warehouseDetail')}
                type="select"
                required
              >
                <option value="" key="0" />
                {warehouseDetails
                  ? warehouseDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="rework-detail-lotDetail"
                name="lotDetail"
                data-cy="lotDetail"
                label={translate('farmicaApp.reworkDetail.lotDetail')}
                type="select"
                required
              >
                <option value="" key="0" />
                {lotDetails
                  ? lotDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="rework-detail-user"
                name="user"
                data-cy="user"
                label={translate('farmicaApp.reworkDetail.user')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rework-detail" replace color="info">
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

export default ReworkDetailUpdate;
