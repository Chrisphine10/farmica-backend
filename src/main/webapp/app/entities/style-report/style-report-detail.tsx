import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './style-report.reducer';

export const StyleReportDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const styleReportEntity = useAppSelector(state => state.styleReport.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="styleReportDetailsHeading">
          <Translate contentKey="farmicaApp.styleReport.detail.title">StyleReport</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{styleReportEntity.id}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="farmicaApp.styleReport.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {styleReportEntity.createdAt ? <TextFormat value={styleReportEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="totalStyleInWarehouse">
              <Translate contentKey="farmicaApp.styleReport.totalStyleInWarehouse">Total Style In Warehouse</Translate>
            </span>
          </dt>
          <dd>{styleReportEntity.totalStyleInWarehouse}</dd>
          <dt>
            <span id="totalStyleInSales">
              <Translate contentKey="farmicaApp.styleReport.totalStyleInSales">Total Style In Sales</Translate>
            </span>
          </dt>
          <dd>{styleReportEntity.totalStyleInSales}</dd>
          <dt>
            <span id="totalStyleInRework">
              <Translate contentKey="farmicaApp.styleReport.totalStyleInRework">Total Style In Rework</Translate>
            </span>
          </dt>
          <dd>{styleReportEntity.totalStyleInRework}</dd>
          <dt>
            <span id="totalStyleInPacking">
              <Translate contentKey="farmicaApp.styleReport.totalStyleInPacking">Total Style In Packing</Translate>
            </span>
          </dt>
          <dd>{styleReportEntity.totalStyleInPacking}</dd>
          <dt>
            <span id="totalStyle">
              <Translate contentKey="farmicaApp.styleReport.totalStyle">Total Style</Translate>
            </span>
          </dt>
          <dd>{styleReportEntity.totalStyle}</dd>
          <dt>
            <Translate contentKey="farmicaApp.styleReport.style">Style</Translate>
          </dt>
          <dd>{styleReportEntity.style ? styleReportEntity.style.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/style-report" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/style-report/${styleReportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StyleReportDetail;
