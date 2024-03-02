import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILotDetail } from 'app/shared/model/lot-detail.model';
import { getEntities as getLotDetails } from 'app/entities/lot-detail/lot-detail.reducer';
import { IStyle } from 'app/shared/model/style.model';
import { getEntities as getStyles } from 'app/entities/style/style.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IPackingZoneDetail } from 'app/shared/model/packing-zone-detail.model';
import { getEntity, updateEntity, createEntity, reset } from './packing-zone-detail.reducer';

export const PackingZoneDetailUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const lotDetails = useAppSelector(state => state.lotDetail.entities);
  const styles = useAppSelector(state => state.style.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const packingZoneDetailEntity = useAppSelector(state => state.packingZoneDetail.entity);
  const loading = useAppSelector(state => state.packingZoneDetail.loading);
  const updating = useAppSelector(state => state.packingZoneDetail.updating);
  const updateSuccess = useAppSelector(state => state.packingZoneDetail.updateSuccess);

  const handleClose = () => {
    navigate('/packing-zone-detail' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLotDetails({}));
    dispatch(getStyles({}));
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
      ...packingZoneDetailEntity,
      ...values,
      lotDetail: lotDetails.find(it => it.id.toString() === values.lotDetail.toString()),
      style: styles.find(it => it.id.toString() === values.style.toString()),
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
          ...packingZoneDetailEntity,
          createdAt: convertDateTimeFromServer(packingZoneDetailEntity.createdAt),
          lotDetail: packingZoneDetailEntity?.lotDetail?.id,
          style: packingZoneDetailEntity?.style?.id,
          user: packingZoneDetailEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmicaApp.packingZoneDetail.home.createOrEditLabel" data-cy="PackingZoneDetailCreateUpdateHeading">
            <Translate contentKey="farmicaApp.packingZoneDetail.home.createOrEditLabel">Create or edit a PackingZoneDetail</Translate>
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
                  id="packing-zone-detail-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.uicode')}
                id="packing-zone-detail-uicode"
                name="uicode"
                data-cy="uicode"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.pdnDate')}
                id="packing-zone-detail-pdnDate"
                name="pdnDate"
                data-cy="pdnDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.packageDate')}
                id="packing-zone-detail-packageDate"
                name="packageDate"
                data-cy="packageDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.weightReceived')}
                id="packing-zone-detail-weightReceived"
                name="weightReceived"
                data-cy="weightReceived"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.weightBalance')}
                id="packing-zone-detail-weightBalance"
                name="weightBalance"
                data-cy="weightBalance"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.numberOfCTNs')}
                id="packing-zone-detail-numberOfCTNs"
                name="numberOfCTNs"
                data-cy="numberOfCTNs"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.receivedCTNs')}
                id="packing-zone-detail-receivedCTNs"
                name="receivedCTNs"
                data-cy="receivedCTNs"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.startCTNNumber')}
                id="packing-zone-detail-startCTNNumber"
                name="startCTNNumber"
                data-cy="startCTNNumber"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.endCTNNumber')}
                id="packing-zone-detail-endCTNNumber"
                name="endCTNNumber"
                data-cy="endCTNNumber"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.numberOfCTNsReworked')}
                id="packing-zone-detail-numberOfCTNsReworked"
                name="numberOfCTNsReworked"
                data-cy="numberOfCTNsReworked"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.numberOfCTNsSold')}
                id="packing-zone-detail-numberOfCTNsSold"
                name="numberOfCTNsSold"
                data-cy="numberOfCTNsSold"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.numberOfCTNsPacked')}
                id="packing-zone-detail-numberOfCTNsPacked"
                name="numberOfCTNsPacked"
                data-cy="numberOfCTNsPacked"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.numberOfCTNsInWarehouse')}
                id="packing-zone-detail-numberOfCTNsInWarehouse"
                name="numberOfCTNsInWarehouse"
                data-cy="numberOfCTNsInWarehouse"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.packingZoneDetail.createdAt')}
                id="packing-zone-detail-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="packing-zone-detail-lotDetail"
                name="lotDetail"
                data-cy="lotDetail"
                label={translate('farmicaApp.packingZoneDetail.lotDetail')}
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
                id="packing-zone-detail-style"
                name="style"
                data-cy="style"
                label={translate('farmicaApp.packingZoneDetail.style')}
                type="select"
                required
              >
                <option value="" key="0" />
                {styles
                  ? styles.map(otherEntity => (
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
                id="packing-zone-detail-user"
                name="user"
                data-cy="user"
                label={translate('farmicaApp.packingZoneDetail.user')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/packing-zone-detail" replace color="info">
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

export default PackingZoneDetailUpdate;
