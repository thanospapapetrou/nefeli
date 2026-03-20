INSERT INTO "REPOSITORY"("URL") SELECT 'https://www.rips-irsp.com/jms/index.php/up/oai/' WHERE NOT EXISTS (SELECT 1 FROM "REPOSITORY" WHERE "URL" = 'https://www.rips-irsp.com/jms/index.php/up/oai/');
INSERT INTO "REPOSITORY"("URL") SELECT 'https://www.ijrah.com/index.php/ijrah/oai' WHERE NOT EXISTS (SELECT 1 FROM "REPOSITORY" WHERE "URL" = 'https://www.ijrah.com/index.php/ijrah/oai');
INSERT INTO "REPOSITORY"("URL") SELECT 'https://repository.universidadean.edu.co/server/oai/request' WHERE NOT EXISTS (SELECT 1 FROM "REPOSITORY" WHERE "URL" = 'https://repository.universidadean.edu.co/server/oai/request');
INSERT INTO "REPOSITORY"("URL") SELECT 'https://repisalud.isciii.es/rest/oai/request' WHERE NOT EXISTS (SELECT 1 FROM "REPOSITORY" WHERE "URL" = 'https://repisalud.isciii.es/rest/oai/request');
INSERT INTO "REPOSITORY"("URL") SELECT 'https://ojs.imeti.org/index.php/EMSI/oai' WHERE NOT EXISTS (SELECT 1 FROM "REPOSITORY" WHERE "URL" = 'https://ojs.imeti.org/index.php/EMSI/oai');
