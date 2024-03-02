import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPackingZoneDetail } from 'app/shared/model/packing-zone-detail.model';
import { getEntities as getPackingZoneDetails } from 'app/entities/packing-zone-detail/packing-zone-detail.reducer';
import { ILotDetail } from 'app/shared/model/lot-detail.model';
import { getEntities as getLotDetails } from 'app/entities/lot-detail/lot-detail.reducer';
import { IStyle } from 'app/shared/model/style.model';
import { getEntities as getStyles } from 'app/entities/style/style.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IWarehouseDetail } from 'app/shared/model/warehouse-detail.model';
import { getEntity, updateEntity, createEntity, reset } from './warehouse-detail.reducer';

export const WarehouseDetailUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const packingZoneDetails = useAppSelector(state => state.packingZoneDetail.entities);
  const lotDetails = useAppSelector(state => state.lotDetail.entities);
  const styles = useAppSelector(state => state.style.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const warehouseDetailEntity = useAppSelector(state => state.warehouseDetail.entity);
  const loading = useAppSelector(state => state.warehouseDetail.loading);
  const updating = useAppSelector(state => state.warehouseDetail.updating);
  const updateSuccess = useAppSelector(state => state.warehouseDetail.updateSuccess);

  const handleClose = () => {
    navigate('/warehouse-detail' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPackingZoneDetails({}));
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
      ...warehouseDetailEntity,
      ...values,
      packingZoneDetail: packingZoneDetails.find(it => it.id.toString() === values.packingZoneDetail.toString()),
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
          ...warehouseDetailEntity,
          createdAt: convertDateTimeFromServer(warehouseDetailEntity.createdAt),
          packingZoneDetail: warehouseDetailEntity?.packingZoneDetail?.id,
          lotDetail: warehouseDetailEntity?.lotDetail?.id,
          style: warehouseDetailEntity?.style?.id,
          user: warehouseDetailEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmicaApp.warehouseDetail.home.createOrEditLabel" data-cy="WarehouseDetailCreateUpdateHeading">
            <Translate contentKey="farmicaApp.warehouseDetail.home.createOrEditLabel">Create or edit a WarehouseDetail</Translate>
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
                  id="warehouse-detail-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmicaApp.warehouseDetail.uicode')}
                id="warehouse-detail-uicode"
                name="uicode"
                data-cy="uicode"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.warehouseDetail.warehouseDate')}
                id="warehouse-detail-warehouseDate"
                name="warehouseDate"
                data-cy="warehouseDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmicaApp.warehouseDetail.numberOfCTNs')}
                id="warehouse-detail-numberOfCTNs"
                name="numberOfCTNs"
                data-cy="numberOfCTNs"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.warehouseDetail.receivedCTNs')}
                id="warehouse-detail-receivedCTNs"
                name="receivedCTNs"
                data-cy="receivedCTNs"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.warehouseDetail.startCTNNumber')}
                id="warehouse-detail-startCTNNumber"
                name="startCTNNumber"
                data-cy="startCTNNumber"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.warehouseDetail.endCTNNumber')}
                id="warehouse-detail-endCTNNumber"
                name="endCTNNumber"
                data-cy="endCTNNumber"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.warehouseDetail.createdAt')}
                id="warehouse-detail-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="warehouse-detail-packingZoneDetail"
                name="packingZoneDetail"
                data-cy="packingZoneDetail"
                label={translate('farmicaApp.warehouseDetail.packingZoneDetail')}
                type="select"
                required
              >
                <option value="" key="0" />
                {packingZoneDetails
                  ? packingZoneDetails.map(otherEntity => (
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
                id="warehouse-detail-lotDetail"
                name="lotDetail"
                data-cy="lotDetail"
                label={translate('farmicaApp.warehouseDetail.lotDetail')}
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
                id="warehouse-detail-style"
                name="style"
                data-cy="style"
                label={translate('farmicaApp.warehouseDetail.style')}
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
                id="warehouse-detail-user"
                name="user"
                data-cy="user"
                label={translate('farmicaApp.warehouseDetail.user')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/warehouse-detail" replace color="info">
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

export default WarehouseDetailUpdate;
