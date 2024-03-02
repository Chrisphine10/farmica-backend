import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStyle } from 'app/shared/model/style.model';
import { getEntities as getStyles } from 'app/entities/style/style.reducer';
import { IStyleReport } from 'app/shared/model/style-report.model';
import { getEntity, updateEntity, createEntity, reset } from './style-report.reducer';

export const StyleReportUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const styles = useAppSelector(state => state.style.entities);
  const styleReportEntity = useAppSelector(state => state.styleReport.entity);
  const loading = useAppSelector(state => state.styleReport.loading);
  const updating = useAppSelector(state => state.styleReport.updating);
  const updateSuccess = useAppSelector(state => state.styleReport.updateSuccess);

  const handleClose = () => {
    navigate('/style-report' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getStyles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...styleReportEntity,
      ...values,
      style: styles.find(it => it.id.toString() === values.style.toString()),
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
          ...styleReportEntity,
          createdAt: convertDateTimeFromServer(styleReportEntity.createdAt),
          style: styleReportEntity?.style?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmicaApp.styleReport.home.createOrEditLabel" data-cy="StyleReportCreateUpdateHeading">
            <Translate contentKey="farmicaApp.styleReport.home.createOrEditLabel">Create or edit a StyleReport</Translate>
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
                  id="style-report-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmicaApp.styleReport.createdAt')}
                id="style-report-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmicaApp.styleReport.totalStyleInWarehouse')}
                id="style-report-totalStyleInWarehouse"
                name="totalStyleInWarehouse"
                data-cy="totalStyleInWarehouse"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.styleReport.totalStyleInSales')}
                id="style-report-totalStyleInSales"
                name="totalStyleInSales"
                data-cy="totalStyleInSales"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.styleReport.totalStyleInRework')}
                id="style-report-totalStyleInRework"
                name="totalStyleInRework"
                data-cy="totalStyleInRework"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.styleReport.totalStyleInPacking')}
                id="style-report-totalStyleInPacking"
                name="totalStyleInPacking"
                data-cy="totalStyleInPacking"
                type="text"
              />
              <ValidatedField
                label={translate('farmicaApp.styleReport.totalStyle')}
                id="style-report-totalStyle"
                name="totalStyle"
                data-cy="totalStyle"
                type="text"
              />
              <ValidatedField
                id="style-report-style"
                name="style"
                data-cy="style"
                label={translate('farmicaApp.styleReport.style')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/style-report" replace color="info">
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

export default StyleReportUpdate;
