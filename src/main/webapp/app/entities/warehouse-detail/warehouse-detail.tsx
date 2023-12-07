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

import { getEntities } from './warehouse-detail.reducer';

export const WarehouseDetail = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const warehouseDetailList = useAppSelector(state => state.warehouseDetail.entities);
  const loading = useAppSelector(state => state.warehouseDetail.loading);

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
      <h2 id="warehouse-detail-heading" data-cy="WarehouseDetailHeading">
        <Translate contentKey="farmicaApp.warehouseDetail.home.title">Warehouse Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmicaApp.warehouseDetail.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/warehouse-detail/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmicaApp.warehouseDetail.home.createLabel">Create new Warehouse Detail</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {warehouseDetailList && warehouseDetailList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="farmicaApp.warehouseDetail.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('uicode')}>
                  <Translate contentKey="farmicaApp.warehouseDetail.uicode">Uicode</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('uicode')} />
                </th>
                <th className="hand" onClick={sort('warehouseDate')}>
                  <Translate contentKey="farmicaApp.warehouseDetail.warehouseDate">Warehouse Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('warehouseDate')} />
                </th>
                <th className="hand" onClick={sort('numberOfCTNs')}>
                  <Translate contentKey="farmicaApp.warehouseDetail.numberOfCTNs">Number Of CT Ns</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('numberOfCTNs')} />
                </th>
                <th className="hand" onClick={sort('receivedCTNs')}>
                  <Translate contentKey="farmicaApp.warehouseDetail.receivedCTNs">Received CT Ns</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('receivedCTNs')} />
                </th>
                <th className="hand" onClick={sort('startCTNNumber')}>
                  <Translate contentKey="farmicaApp.warehouseDetail.startCTNNumber">Start CTN Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startCTNNumber')} />
                </th>
                <th className="hand" onClick={sort('endCTNNumber')}>
                  <Translate contentKey="farmicaApp.warehouseDetail.endCTNNumber">End CTN Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('endCTNNumber')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="farmicaApp.warehouseDetail.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.warehouseDetail.packingZoneDetail">Packing Zone Detail</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.warehouseDetail.lotDetail">Lot Detail</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.warehouseDetail.style">Style</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.warehouseDetail.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {warehouseDetailList.map((warehouseDetail, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/warehouse-detail/${warehouseDetail.id}`} color="link" size="sm">
                      {warehouseDetail.id}
                    </Button>
                  </td>
                  <td>{warehouseDetail.uicode}</td>
                  <td>
                    {warehouseDetail.warehouseDate ? (
                      <TextFormat type="date" value={warehouseDetail.warehouseDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{warehouseDetail.numberOfCTNs}</td>
                  <td>{warehouseDetail.receivedCTNs}</td>
                  <td>{warehouseDetail.startCTNNumber}</td>
                  <td>{warehouseDetail.endCTNNumber}</td>
                  <td>
                    {warehouseDetail.createdAt ? (
                      <TextFormat type="date" value={warehouseDetail.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {warehouseDetail.packingZoneDetail ? (
                      <Link to={`/packing-zone-detail/${warehouseDetail.packingZoneDetail.id}`}>
                        {warehouseDetail.packingZoneDetail.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {warehouseDetail.lotDetail ? (
                      <Link to={`/lot-detail/${warehouseDetail.lotDetail.id}`}>{warehouseDetail.lotDetail.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{warehouseDetail.style ? <Link to={`/style/${warehouseDetail.style.id}`}>{warehouseDetail.style.id}</Link> : ''}</td>
                  <td>{warehouseDetail.user ? warehouseDetail.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/warehouse-detail/${warehouseDetail.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/warehouse-detail/${warehouseDetail.id}/edit`}
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
                        to={`/warehouse-detail/${warehouseDetail.id}/delete`}
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
              <Translate contentKey="farmicaApp.warehouseDetail.home.notFound">No Warehouse Details found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default WarehouseDetail;
