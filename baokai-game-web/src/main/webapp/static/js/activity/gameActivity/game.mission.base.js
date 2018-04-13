(function () {
    window.GameMission = function (OldMission) {
        var GameMission = {
            noConflict: noConflict
        };
        return GameMission;

        function noConflict() {
            if (window.GameMission === GameMission) {
                window.GameMission = OldMission;
            }
            return GameMission;
        }
    }(window.GameMission);

    GameMission.Client = function () {
        function Client(missionCode) {
            this.missionCode = missionCode;
        }

        Client.prototype.queryMissions = function (callback) {
            var data = {};
            $.ajax({
                url: '/gamemission/queryMissions',
                dataType: 'json',
                data:{missionCode:this.missionCode},
                success: function (result) {
                    data = result['missions'];
                    for(var i in data){
                        var mission = data[i];
                        mission['params'] = JSON.parse(mission['params']);
                    }
                    callback(data);
                }
            });
        };

        Client.prototype.queryUserMissionData = function (callback) {
            var data = {};
            $.ajax({
                url: '/gamemission/querUserMissionData',
                dataType: 'json',
                data:{missionCode:this.missionCode},
                success: function (result) {
                    data = result['userDatas'];
                    for(var i in data){
                        var userData = data[i];
                        userData['targets'] = JSON.parse(userData['targets']);
                    }
                    callback(data);
                }
            });
        };

        Client.prototype.completeMissions = function (itemSeq,callback) {
            var data = {};
            $.ajax({
                url: '/gamemission/completeMission',
                dataType: 'json',
                data:{missionCode:this.missionCode,itemSeq:itemSeq},
                success: function (result) {
                    callback(result['status']);
                }
            });
            return data;
        };

        return Client;
    }();
})()