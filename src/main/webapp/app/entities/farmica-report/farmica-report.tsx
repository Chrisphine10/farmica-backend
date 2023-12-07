import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './farmica-report.reducer';

export const FarmicaReport = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const farmicaReportList = useAppSelector(state => state.farmicaReport.entities);
  const loading = useAppSelector(state => state.farmicaReport.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="farmica-report-heading" data-cy="FarmicaReportHeading">
        <Translate contentKey="farmicaApp.farmicaReport.home.title">Farmica Reports</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmicaApp.farmicaReport.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/farmica-report/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmicaApp.farmicaReport.home.createLabel">Create new Farmica Report</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {farmicaReportList && farmicaReportList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="farmicaApp.farmicaReport.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="farmicaApp.farmicaReport.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('totalItemsInWarehouse')}>
                  <Translate contentKey="farmicaApp.farmicaReport.totalItemsInWarehouse">Total Items In Warehouse</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalItemsInWarehouse')} />
                </th>
                <th className="hand" onClick={sort('totalItemsInSales')}>
                  <Translate contentKey="farmicaApp.farmicaReport.totalItemsInSales">Total Items In Sales</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalItemsInSales')} />
                </th>
                <th className="hand" onClick={sort('totalItemsInRework')}>
                  <Translate contentKey="farmicaApp.farmicaReport.totalItemsInRework">Total Items In Rework</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalItemsInRework')} />
                </th>
                <th className="hand" onClick={sort('totalItemsInPacking')}>
                  <Translate contentKey="farmicaApp.farmicaReport.totalItemsInPacking">Total Items In Packing</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalItemsInPacking')} />
                </th>
                <th className="hand" onClick={sort('totalItems')}>
                  <Translate contentKey="farmicaApp.farmicaReport.totalItems">Total Items</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalItems')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {farmicaReportList.map((farmicaReport, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/farmica-report/${farmicaReport.id}`} color="link" size="sm">
                      {farmicaReport.id}
                    </Button>
                  </td>
                  <td>
                    {farmicaReport.createdAt ? <TextFormat type="date" value={farmicaReport.createdAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{farmicaReport.totalItemsInWarehouse}</td>
                  <td>{farmicaReport.totalItemsInSales}</td>
                  <td>{farmicaReport.totalItemsInRework}</td>
                  <td>{farmicaReport.totalItemsInPacking}</td>
                  <td>{farmicaReport.totalItems}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/farmica-report/${farmicaReport.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/farmica-report/${farmicaReport.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/farmica-report/${farmicaReport.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="farmicaApp.farmicaReport.home.notFound">No Farmica Reports found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FarmicaReport;
