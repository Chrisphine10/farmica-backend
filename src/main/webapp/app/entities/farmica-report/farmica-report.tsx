import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './farmica-report.reducer';

export const FarmicaReport = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const farmicaReportList = useAppSelector(state => state.farmicaReport.entities);
  const loading = useAppSelector(state => state.farmicaReport.loading);
  const totalItems = useAppSelector(state => state.farmicaReport.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
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
                        to={`/farmica-report/${farmicaReport.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/farmica-report/${farmicaReport.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
      {totalItems ? (
        <div className={farmicaReportList && farmicaReportList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default FarmicaReport;
