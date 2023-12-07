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

import { getEntities } from './sales-detail.reducer';

export const SalesDetail = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const salesDetailList = useAppSelector(state => state.salesDetail.entities);
  const loading = useAppSelector(state => state.salesDetail.loading);

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
      <h2 id="sales-detail-heading" data-cy="SalesDetailHeading">
        <Translate contentKey="farmicaApp.salesDetail.home.title">Sales Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmicaApp.salesDetail.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/sales-detail/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmicaApp.salesDetail.home.createLabel">Create new Sales Detail</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {salesDetailList && salesDetailList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="farmicaApp.salesDetail.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('uicode')}>
                  <Translate contentKey="farmicaApp.salesDetail.uicode">Uicode</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('uicode')} />
                </th>
                <th className="hand" onClick={sort('salesDate')}>
                  <Translate contentKey="farmicaApp.salesDetail.salesDate">Sales Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('salesDate')} />
                </th>
                <th className="hand" onClick={sort('numberOfCTNs')}>
                  <Translate contentKey="farmicaApp.salesDetail.numberOfCTNs">Number Of CT Ns</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('numberOfCTNs')} />
                </th>
                <th className="hand" onClick={sort('receivedCTNs')}>
                  <Translate contentKey="farmicaApp.salesDetail.receivedCTNs">Received CT Ns</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('receivedCTNs')} />
                </th>
                <th className="hand" onClick={sort('startCTNNumber')}>
                  <Translate contentKey="farmicaApp.salesDetail.startCTNNumber">Start CTN Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startCTNNumber')} />
                </th>
                <th className="hand" onClick={sort('endCTNNumber')}>
                  <Translate contentKey="farmicaApp.salesDetail.endCTNNumber">End CTN Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('endCTNNumber')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="farmicaApp.salesDetail.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.salesDetail.warehouseDetail">Warehouse Detail</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.salesDetail.lotDetail">Lot Detail</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.salesDetail.style">Style</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.salesDetail.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {salesDetailList.map((salesDetail, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sales-detail/${salesDetail.id}`} color="link" size="sm">
                      {salesDetail.id}
                    </Button>
                  </td>
                  <td>{salesDetail.uicode}</td>
                  <td>
                    {salesDetail.salesDate ? <TextFormat type="date" value={salesDetail.salesDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{salesDetail.numberOfCTNs}</td>
                  <td>{salesDetail.receivedCTNs}</td>
                  <td>{salesDetail.startCTNNumber}</td>
                  <td>{salesDetail.endCTNNumber}</td>
                  <td>
                    {salesDetail.createdAt ? <TextFormat type="date" value={salesDetail.createdAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {salesDetail.warehouseDetail ? (
                      <Link to={`/warehouse-detail/${salesDetail.warehouseDetail.id}`}>{salesDetail.warehouseDetail.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {salesDetail.lotDetail ? <Link to={`/lot-detail/${salesDetail.lotDetail.id}`}>{salesDetail.lotDetail.id}</Link> : ''}
                  </td>
                  <td>{salesDetail.style ? <Link to={`/style/${salesDetail.style.id}`}>{salesDetail.style.id}</Link> : ''}</td>
                  <td>{salesDetail.user ? salesDetail.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/sales-detail/${salesDetail.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/sales-detail/${salesDetail.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sales-detail/${salesDetail.id}/delete`}
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
              <Translate contentKey="farmicaApp.salesDetail.home.notFound">No Sales Details found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SalesDetail;
