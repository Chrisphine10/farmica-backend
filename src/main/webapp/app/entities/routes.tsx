import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PackingZoneDetail from './packing-zone-detail';
import Comment from './comment';
import ReworkComment from './rework-comment';
import ReworkDetail from './rework-detail';
import LotDetail from './lot-detail';
import VariableData from './variable-data';
import BatchDetail from './batch-detail';
import Region from './region';
import Style from './style';
import SalesDetail from './sales-detail';
import WarehouseDetail from './warehouse-detail';
import FarmicaReport from './farmica-report';
import StyleReport from './style-report';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="packing-zone-detail/*" element={<PackingZoneDetail />} />
        <Route path="comment/*" element={<Comment />} />
        <Route path="rework-comment/*" element={<ReworkComment />} />
        <Route path="rework-detail/*" element={<ReworkDetail />} />
        <Route path="lot-detail/*" element={<LotDetail />} />
        <Route path="variable-data/*" element={<VariableData />} />
        <Route path="batch-detail/*" element={<BatchDetail />} />
        <Route path="region/*" element={<Region />} />
        <Route path="style/*" element={<Style />} />
        <Route path="sales-detail/*" element={<SalesDetail />} />
        <Route path="warehouse-detail/*" element={<WarehouseDetail />} />
        <Route path="farmica-report/*" element={<FarmicaReport />} />
        <Route path="style-report/*" element={<StyleReport />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
