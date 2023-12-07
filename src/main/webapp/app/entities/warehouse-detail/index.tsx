import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WarehouseDetail from './warehouse-detail';
import WarehouseDetailDetail from './warehouse-detail-detail';
import WarehouseDetailUpdate from './warehouse-detail-update';
import WarehouseDetailDeleteDialog from './warehouse-detail-delete-dialog';

const WarehouseDetailRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WarehouseDetail />} />
    <Route path="new" element={<WarehouseDetailUpdate />} />
    <Route path=":id">
      <Route index element={<WarehouseDetailDetail />} />
      <Route path="edit" element={<WarehouseDetailUpdate />} />
      <Route path="delete" element={<WarehouseDetailDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WarehouseDetailRoutes;
