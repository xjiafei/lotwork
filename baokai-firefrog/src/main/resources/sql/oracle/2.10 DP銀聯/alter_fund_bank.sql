alter table fund_bank add   move_quick_type number;
alter table fund_bank add   move_quick_deposit number;
alter table fund_bank add   move_deposit number;
COMMENT ON COLUMN FIREFOG.fund_bank.move_quick_type IS '移動端快捷充值類 0:否 1:是';
COMMENT ON COLUMN FIREFOG.fund_bank.move_quick_deposit IS '移動端快捷充值 0:否 1:是';
COMMENT ON COLUMN FIREFOG.fund_bank.move_deposit IS '移動端開關充值 0:否 1:是';
