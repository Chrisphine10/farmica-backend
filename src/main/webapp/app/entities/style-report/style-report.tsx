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

import { getEntities } from './style-report.reducer';

export const StyleReport = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const styleReportList = useAppSelector(state => state.styleReport.entities);
  const loading = useAppSelector(state => state.styleReport.loading);

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
      <h2 id="style-report-heading" data-cy="StyleReportHeading">
        <Translate contentKey="farmicaApp.styleReport.home.title">Style Reports</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmicaApp.styleReport.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/style-report/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmicaApp.styleReport.home.createLabel">Create new Style Report</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {styleReportList && styleReportList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="farmicaApp.styleReport.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="farmicaApp.styleReport.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('totalStyleInWarehouse')}>
                  <Translate contentKey="farmicaApp.styleReport.totalStyleInWarehouse">Total Style In Warehouse</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalStyleInWarehouse')} />
                </th>
                <th className="hand" onClick={sort('totalStyleInSales')}>
                  <Translate contentKey="farmicaApp.styleReport.totalStyleInSales">Total Style In Sales</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalStyleInSales')} />
                </th>
                <th className="hand" onClick={sort('totalStyleInRework')}>
                  <Translate contentKey="farmicaApp.styleReport.totalStyleInRework">Total Style In Rework</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalStyleInRework')} />
                </th>
                <th className="hand" onClick={sort('totalStyleInPacking')}>
                  <Translate contentKey="farmicaApp.styleReport.totalStyleInPacking">Total Style In Packing</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalStyleInPacking')} />
                </th>
                <th className="hand" onClick={sort('totalStyle')}>
                  <Translate contentKey="farmicaApp.styleReport.totalStyle">Total Style</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalStyle')} />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.styleReport.style">Style</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {styleReportList.map((styleReport, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/style-report/${styleReport.id}`} color="link" size="sm">
                      {styleReport.id}
                    </Button>
                  </td>
                  <td>
                    {styleReport.createdAt ? <TextFormat type="date" value={styleReport.createdAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{styleReport.totalStyleInWarehouse}</td>
                  <td>{styleReport.totalStyleInSales}</td>
                  <td>{styleReport.totalStyleInRework}</td>
                  <td>{styleReport.totalStyleInPacking}</td>
                  <td>{styleReport.totalStyle}</td>
                  <td>{styleReport.style ? <Link to={`/style/${styleReport.style.id}`}>{styleReport.style.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/style-report/${styleReport.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/style-report/${styleReport.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/style-report/${styleReport.id}/delete`}
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
              <Translate contentKey="farmicaApp.styleReport.home.notFound">No Style Reports found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default StyleReport;
