import packingZoneDetail from 'app/entities/packing-zone-detail/packing-zone-detail.reducer';
import comment from 'app/entities/comment/comment.reducer';
import reworkComment from 'app/entities/rework-comment/rework-comment.reducer';
import reworkDetail from 'app/entities/rework-detail/rework-detail.reducer';
import lotDetail from 'app/entities/lot-detail/lot-detail.reducer';
import variableData from 'app/entities/variable-data/variable-data.reducer';
import batchDetail from 'app/entities/batch-detail/batch-detail.reducer';
import region from 'app/entities/region/region.reducer';
import style from 'app/entities/style/style.reducer';
import salesDetail from 'app/entities/sales-detail/sales-detail.reducer';
import warehouseDetail from 'app/entities/warehouse-detail/warehouse-detail.reducer';
import farmicaReport from 'app/entities/farmica-report/farmica-report.reducer';
import styleReport from 'app/entities/style-report/style-report.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  packingZoneDetail,
  comment,
  reworkComment,
  reworkDetail,
  lotDetail,
  variableData,
  batchDetail,
  region,
  style,
  salesDetail,
  warehouseDetail,
  farmicaReport,
  styleReport,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
