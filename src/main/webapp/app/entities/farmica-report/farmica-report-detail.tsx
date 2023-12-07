import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './farmica-report.reducer';

export const FarmicaReportDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const farmicaReportEntity = useAppSelector(state => state.farmicaReport.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="farmicaReportDetailsHeading">
          <Translate contentKey="farmicaApp.farmicaReport.detail.title">FarmicaReport</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{farmicaReportEntity.id}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="farmicaApp.farmicaReport.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {farmicaReportEntity.createdAt ? (
              <TextFormat value={farmicaReportEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="totalItemsInWarehouse">
              <Translate contentKey="farmicaApp.farmicaReport.totalItemsInWarehouse">Total Items In Warehouse</Translate>
            </span>
          </dt>
          <dd>{farmicaReportEntity.totalItemsInWarehouse}</dd>
          <dt>
            <span id="totalItemsInSales">
              <Translate contentKey="farmicaApp.farmicaReport.totalItemsInSales">Total Items In Sales</Translate>
            </span>
          </dt>
          <dd>{farmicaReportEntity.totalItemsInSales}</dd>
          <dt>
            <span id="totalItemsInRework">
              <Translate contentKey="farmicaApp.farmicaReport.totalItemsInRework">Total Items In Rework</Translate>
            </span>
          </dt>
          <dd>{farmicaReportEntity.totalItemsInRework}</dd>
          <dt>
            <span id="totalItemsInPacking">
              <Translate contentKey="farmicaApp.farmicaReport.totalItemsInPacking">Total Items In Packing</Translate>
            </span>
          </dt>
          <dd>{farmicaReportEntity.totalItemsInPacking}</dd>
          <dt>
            <span id="totalItems">
              <Translate contentKey="farmicaApp.farmicaReport.totalItems">Total Items</Translate>
            </span>
          </dt>
          <dd>{farmicaReportEntity.totalItems}</dd>
        </dl>
        <Button tag={Link} to="/farmica-report" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/farmica-report/${farmicaReportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FarmicaReportDetail;
