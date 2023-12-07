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
import { IStyle } from 'app/shared/model/style.model';
import { getEntities as getStyles } from 'app/entities/style/style.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ISalesDetail } from 'app/shared/model/sales-detail.model';
import { getEntity, updateEntity, createEntity, reset } from './sales-detail.reducer';

export const SalesDetailUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const warehouseDetails = useAppSelector(state => state.warehouseDetail.entities);
  const lotDetails = useAppSelector(state => state.lotDetail.entities);
  const styles = useAppSelector(state => state.style.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const salesDetailEntity = useAppSelector(state => state.salesDetail.entity);
  const loading = useAppSelector(state => state.salesDetail.loading);
  const updating = useAppSelector(state => state.salesDetail.updating);
  const updateSuccess = useAppSelector(state => state.salesDetail.updateSuccess);

  const handleClose = () => {
    navigate('/sales-detail');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWarehouseDetails({}));
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
      ...salesDetailEntity,
      ...values,
      warehouseDetail: warehouseDetails.find(it => it.id.toString() === values.warehouseDetail.toString()),
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
          ...salesDetailEntity,
          createdAt: convertDateTimeFromServer(salesDetailEntity.createdAt),
          warehouseDetail: salesDetailEntity?.warehouseDetail?.id,
          lotDetail: salesDetailEntity?.lotDetail?.id,
          style: salesDetailEntity?.style?.id,
          user: salesDetailEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmicaApp.salesDetail.home.createOrEditLabel" data-cy="SalesDetailCreateUpdateHeading">
            <Translate contentKey="farmicaApp.salesDetail.home.createOrEditLabel">Create or edit a SalesDetail</Translate>
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
                  id="sales-detail-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmicaApp.salesDetail.uicode')}
                id="sales-detail-uicode"
                name="uicode"
                data-cy="uicode"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.salesDetail.salesDate')}
                id="sales-detail-salesDate"
                name="salesDate"
                data-cy="salesDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmicaApp.salesDetail.numberOfCTNs')}
                id="sales-detail-numberOfCTNs"
                name="numberOfCTNs"
                data-cy="numberOfCTNs"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('farmicaApp.salesDetail.receivedCTNs')}
                id="sales-detail-receivedCTNs"
                name="receivedCTNs"
                data-cy="receivedCTNs"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.salesDetail.startCTNNumber')}
                id="sales-detail-startCTNNumber"
                name="startCTNNumber"
                data-cy="startCTNNumber"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.salesDetail.endCTNNumber')}
                id="sales-detail-endCTNNumber"
                name="endCTNNumber"
                data-cy="endCTNNumber"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.salesDetail.createdAt')}
                id="sales-detail-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="sales-detail-warehouseDetail"
                name="warehouseDetail"
                data-cy="warehouseDetail"
                label={translate('farmicaApp.salesDetail.warehouseDetail')}
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
                id="sales-detail-lotDetail"
                name="lotDetail"
                data-cy="lotDetail"
                label={translate('farmicaApp.salesDetail.lotDetail')}
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
                id="sales-detail-style"
                name="style"
                data-cy="style"
                label={translate('farmicaApp.salesDetail.style')}
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
                id="sales-detail-user"
                name="user"
                data-cy="user"
                label={translate('farmicaApp.salesDetail.user')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sales-detail" replace color="info">
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

export default SalesDetailUpdate;
