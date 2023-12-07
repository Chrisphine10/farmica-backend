import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FarmicaReport from './farmica-report';
import FarmicaReportDetail from './farmica-report-detail';
import FarmicaReportUpdate from './farmica-report-update';
import FarmicaReportDeleteDialog from './farmica-report-delete-dialog';

const FarmicaReportRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FarmicaReport />} />
    <Route path="new" element={<FarmicaReportUpdate />} />
    <Route path=":id">
      <Route index element={<FarmicaReportDetail />} />
      <Route path="edit" element={<FarmicaReportUpdate />} />
      <Route path="delete" element={<FarmicaReportDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FarmicaReportRoutes;
