import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ReworkDetail from './rework-detail';
import ReworkDetailDetail from './rework-detail-detail';
import ReworkDetailUpdate from './rework-detail-update';
import ReworkDetailDeleteDialog from './rework-detail-delete-dialog';

const ReworkDetailRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ReworkDetail />} />
    <Route path="new" element={<ReworkDetailUpdate />} />
    <Route path=":id">
      <Route index element={<ReworkDetailDetail />} />
      <Route path="edit" element={<ReworkDetailUpdate />} />
      <Route path="delete" element={<ReworkDetailDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReworkDetailRoutes;
