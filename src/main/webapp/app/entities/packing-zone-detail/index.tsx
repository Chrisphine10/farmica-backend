import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PackingZoneDetail from './packing-zone-detail';
import PackingZoneDetailDetail from './packing-zone-detail-detail';
import PackingZoneDetailUpdate from './packing-zone-detail-update';
import PackingZoneDetailDeleteDialog from './packing-zone-detail-delete-dialog';

const PackingZoneDetailRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PackingZoneDetail />} />
    <Route path="new" element={<PackingZoneDetailUpdate />} />
    <Route path=":id">
      <Route index element={<PackingZoneDetailDetail />} />
      <Route path="edit" element={<PackingZoneDetailUpdate />} />
      <Route path="delete" element={<PackingZoneDetailDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PackingZoneDetailRoutes;
