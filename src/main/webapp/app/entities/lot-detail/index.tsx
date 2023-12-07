import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LotDetail from './lot-detail';
import LotDetailDetail from './lot-detail-detail';
import LotDetailUpdate from './lot-detail-update';
import LotDetailDeleteDialog from './lot-detail-delete-dialog';

const LotDetailRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LotDetail />} />
    <Route path="new" element={<LotDetailUpdate />} />
    <Route path=":id">
      <Route index element={<LotDetailDetail />} />
      <Route path="edit" element={<LotDetailUpdate />} />
      <Route path="delete" element={<LotDetailDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LotDetailRoutes;
