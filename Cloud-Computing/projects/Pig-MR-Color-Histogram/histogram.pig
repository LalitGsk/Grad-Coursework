inp = LOAD '$P' USING PigStorage(',') 
AS 
(r_intensity:long, g_intensity:long, b_intensity:long);

rgb_map = FOREACH inp GENERATE FLATTEN({(1,r_intensity), (2,g_intensity), (3,b_intensity)});
combined = ORDER(GROUP rgb_map BY ($0,$1)) BY $0 ASC,$1 ASC;
collect_rgb = FOREACH combined GENERATE $0.$0, $0.$1, COUNT($1);
STORE collect_rgb INTO '$O' USING PigStorage(',');
