package io.github.andrew6rant.weather;

// MIT License

//
// Copyright(c) 2020 Jordan Peck (jordan.me2@gmail.com)
// Copyright(c) 2020 Contributors
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files(the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions :
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//
// .'',;:cldxkO00KKXXNNWWWNNXKOkxdollcc::::::;:::ccllloooolllllllllooollc:,'...        ...........',;cldxkO000Okxdlc::;;;,,;;;::cclllllll
// ..',;:ldxO0KXXNNNNNNNNXXK0kxdolcc::::::;;;,,,,,,;;;;;;;;;;:::cclllllc:;'....       ...........',;:ldxO0KXXXK0Okxdolc::;;;;::cllodddddo
// ...',:loxO0KXNNNNNXXKK0Okxdolc::;::::::::;;;,,'''''.....''',;:clllllc:;,'............''''''''',;:loxO0KXNNNNNXK0Okxdollccccllodxxxxxxd
// ....';:ldkO0KXXXKK00Okxdolcc:;;;;;::cclllcc:;;,''..... ....',;clooddolcc:;;;;,,;;;;;::::;;;;;;:cloxk0KXNWWWWWWNXKK0Okxddoooddxxkkkkkxx
// .....';:ldxkOOOOOkxxdolcc:;;;,,,;;:cllooooolcc:;'...      ..,:codxkkkxddooollloooooooollcc:::::clodkO0KXNWWWWWWNNXK00Okxxxxxxxxkkkkxxx
// . ....';:cloddddo___________,,,,;;:clooddddoolc:,...      ..,:ldx__00OOOkkk___kkkkkkxxdollc::::cclodkO0KXXNNNNNNXXK0OOkxxxxxxxxxxxxddd
// .......',;:cccc:|           |,,,;;:cclooddddoll:;'..     ..';cox|  \KKK000|   |KK00OOkxdocc___;::clldxxkO0KKKKK00Okkxdddddddddddddddoo
// .......'',,,,,''|   ________|',,;;::cclloooooolc:;'......___:ldk|   \KK000|   |XKKK0Okxolc|   |;;::cclodxxkkkkxxdoolllcclllooodddooooo
// ''......''''....|   |  ....'',,,,;;;::cclloooollc:;,''.'|   |oxk|    \OOO0|   |KKK00Oxdoll|___|;;;;;::ccllllllcc::;;,,;;;:cclloooooooo
// ;;,''.......... |   |_____',,;;;____:___cllo________.___|   |___|     \xkk|   |KK_______ool___:::;________;;;_______...'',;;:ccclllloo
// c:;,''......... |         |:::/     '   |lo/        |           |      \dx|   |0/       \d|   |cc/        |'/       \......',,;;:ccllo
// ol:;,'..........|    _____|ll/    __    |o/   ______|____    ___|   |   \o|   |/   ___   \|   |o/   ______|/   ___   \ .......'',;:clo
// dlc;,...........|   |::clooo|    /  |   |x\___   \KXKKK0|   |dol|   |\   \|   |   |   |   |   |d\___   \..|   |  /   /       ....',:cl
// xoc;'...  .....'|   |llodddd|    \__|   |_____\   \KKK0O|   |lc:|   |'\       |   |___|   |   |_____\   \.|   |_/___/...      ...',;:c
// dlc;'... ....',;|   |oddddddo\          |          |Okkx|   |::;|   |..\      |\         /|   |          | \         |...    ....',;:c
// ol:,'.......',:c|___|xxxddollc\_____,___|_________/ddoll|___|,,,|___|...\_____|:\ ______/l|___|_________/...\________|'........',;::cc
// c:;'.......';:codxxkkkkxxolc::;::clodxkOO0OOkkxdollc::;;,,''''',,,,''''''''''',,'''''',;:loxkkOOkxol:;,'''',,;:ccllcc:;,'''''',;::ccll
// ;,'.......',:codxkOO0OOkxdlc:;,,;;:cldxxkkxxdolc:;;,,''.....'',;;:::;;,,,'''''........,;cldkO0KK0Okdoc::;;::cloodddoolc:;;;;;::ccllooo
// .........',;:lodxOO0000Okdoc:,,',,;:clloddoolc:;,''.......'',;:clooollc:;;,,''.......',:ldkOKXNNXX0Oxdolllloddxxxxxxdolccccccllooodddd
// .    .....';:cldxkO0000Okxol:;,''',,;::cccc:;,,'.......'',;:cldxxkkxxdolc:;;,'.......';coxOKXNWWWNXKOkxddddxxkkkkkkxdoollllooddxxxxkkk
//       ....',;:codxkO000OOxdoc:;,''',,,;;;;,''.......',,;:clodkO00000Okxolc::;,,''..',;:ldxOKXNWWWNNK0OkkkkkkkkkkkxxddooooodxxkOOOOO000
//       ....',;;clodxkkOOOkkdolc:;,,,,,,,,'..........,;:clodxkO0KKXKK0Okxdolcc::;;,,,;;:codkO0XXNNNNXKK0OOOOOkkkkxxdoollloodxkO0KKKXXXXX
//
// VERSION: 1.0.1
// https://github.com/Auburn/FastNoise

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class FastNoiseSampler {
    public static final Codec<FastNoiseSampler> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.BOOL.fieldOf("invert").stable().forGetter((fastNoise) -> {
            return fastNoise.invert;
        }), Codec.LONG.fieldOf("seed").stable().forGetter((fastNoise) -> {
            return fastNoise.mSeed;
        }), Codec.DOUBLE.fieldOf("frequency").stable().forGetter((fastNoise) -> {
            return fastNoise.mFrequency;
        }), Codec.STRING.fieldOf("noise_type").stable().forGetter((fastNoise) -> {
            return fastNoise.mNoiseType.name();
        }), Codec.STRING.fieldOf("rotation_type_3d").stable().forGetter((fastNoise) -> {
            return fastNoise.mRotationType3D.name();
        }), Codec.STRING.fieldOf("fractal_type").stable().forGetter((fastNoise) -> {
            return fastNoise.mFractalType.name();
        }), Codec.INT.fieldOf("octaves").stable().forGetter((fastNoise) -> {
            return fastNoise.mOctaves;
        }), Codec.DOUBLE.fieldOf("lacunarity").stable().forGetter((fastNoise) -> {
            return fastNoise.mLacunarity;
        }), Codec.DOUBLE.fieldOf("gain").stable().forGetter((fastNoise) -> {
            return fastNoise.mGain;
        }), Codec.DOUBLE.fieldOf("weighted_strength").stable().forGetter((fastNoise) -> {
            return fastNoise.mWeightedStrength;
        }), Codec.DOUBLE.fieldOf("ping_pong_strength").stable().forGetter((fastNoise) -> {
            return fastNoise.mPingPongStrength;
        }), Codec.STRING.fieldOf("cellular_distance_function").stable().forGetter((fastNoise) -> {
            return fastNoise.mCellularDistanceFunction.name();
        }), Codec.STRING.fieldOf("cellular_return_type").stable().forGetter((fastNoise) -> {
            return fastNoise.mCellularReturnType.name();
        }), Codec.DOUBLE.fieldOf("cellular_jitter_modifier").stable().forGetter((fastNoise) -> {
            return fastNoise.mCellularJitterModifier;
        }), Codec.STRING.fieldOf("domain_warp_type").stable().forGetter((fastNoise) -> {
            return fastNoise.mDomainWarpType.name();
        }), Codec.DOUBLE.fieldOf("domain_warp_amp").stable().forGetter((fastNoise) -> {
            return fastNoise.mDomainWarpAmp;
        })).apply(instance, instance.stable((invert, seed, frequency, noiseType, rotationType3D, fractalType, octaves, lacunarity, gain, weightedStrength, pingPongStrength, cellularDistanceFunction, cellularReturnType, cellularJitterModifier, domainWarpType, domainWarpAmp) -> {
            FastNoiseSampler noise = new FastNoiseSampler(seed);
            noise.setInvert(invert);
            noise.SetFrequency(frequency);
            noise.SetFractalOctaves(octaves);
            noise.SetFractalLacunarity(lacunarity);
            noise.SetFractalGain(gain);
            noise.SetFractalWeightedStrength(weightedStrength);
            noise.SetFractalPingPongStrength(pingPongStrength);
            noise.SetCellularJitter(cellularJitterModifier);
            noise.SetDomainWarpAmp(domainWarpAmp);
            noise.SetNoiseType(NoiseType.valueOf(noiseType));
            noise.SetRotationType3D(RotationType3D.valueOf(rotationType3D));
            noise.SetFractalType(FractalType.valueOf(fractalType));
            noise.SetCellularDistanceFunction(CellularDistanceFunction.valueOf(cellularDistanceFunction));
            noise.SetCellularReturnType(CellularReturnType.valueOf(cellularReturnType));
            noise.SetDomainWarpType(DomainWarpType.valueOf(domainWarpType));
            return noise;
        }));
    });

    public enum NoiseType {
        OpenSimplex2, OpenSimplex2S, Cellular, Perlin, ValueCubic, Value
    };

    public enum RotationType3D {
        None, ImproveXYPlanes, ImproveXZPlanes
    };

    public enum FractalType {
        None, FBm, Ridged, PingPong, DomainWarpProgressive, DomainWarpIndependent
    };

    public enum CellularDistanceFunction {
        Euclidean, EuclideanSq, Manhattan, Hybrid
    };

    public enum CellularReturnType {
        CellValue, Distance, Distance2, Distance2Add, Distance2Sub, Distance2Mul, Distance2Div
    };

    public enum DomainWarpType {
        OpenSimplex2, OpenSimplex2Reduced, BasicGrid
    };

    private enum TransformType3D {
        None, ImproveXYPlanes, ImproveXZPlanes, DefaultOpenSimplex2
    };

    private boolean invert = false;
    private long mSeed = 1337;
    private double mFrequency = 0.01d;
    private NoiseType mNoiseType = NoiseType.OpenSimplex2;
    private RotationType3D mRotationType3D = RotationType3D.None;
    private TransformType3D mTransformType3D = TransformType3D.DefaultOpenSimplex2;

    private FractalType mFractalType = FractalType.None;
    private int mOctaves = 3;
    private double mLacunarity = 2.0d;
    private double mGain = 0.5d;
    private double mWeightedStrength = 0.0d;
    private double mPingPongStrength = 2.0d;

    private double mFractalBounding = 1 / 1.75d;

    private CellularDistanceFunction mCellularDistanceFunction = CellularDistanceFunction.EuclideanSq;
    private CellularReturnType mCellularReturnType = CellularReturnType.Distance;
    private double mCellularJitterModifier = 1.0d;

    private DomainWarpType mDomainWarpType = DomainWarpType.OpenSimplex2;
    private TransformType3D mWarpTransformType3D = TransformType3D.DefaultOpenSimplex2;
    private double mDomainWarpAmp = 1.0d;

    public FastNoiseSampler() {
    }

    public FastNoiseSampler(long seed) {
        SetSeed(seed);
    }

    public static FastNoiseSampler create(boolean invert, long seed, NoiseType noiseType, RotationType3D rotationType3D, double frequency, FractalType fractalType, int octaves, double lacunarity, double gain, double weightedStrength, double pingPongStrength, CellularDistanceFunction cellularDistanceFunction, CellularReturnType cellularReturnType, double cellularJitterModifier, DomainWarpType domainWarpType, double domainWarpAmp) {
        FastNoiseSampler noise = new FastNoiseSampler(seed);
        noise.setInvert(invert);
        noise.SetFrequency(frequency);
        noise.SetFractalOctaves(octaves);
        noise.SetFractalLacunarity(lacunarity);
        noise.SetFractalGain(gain);
        noise.SetFractalWeightedStrength(weightedStrength);
        noise.SetFractalPingPongStrength(pingPongStrength);
        noise.SetCellularJitter(cellularJitterModifier);
        noise.SetDomainWarpAmp(domainWarpAmp);
        noise.SetNoiseType(noiseType);
        noise.SetRotationType3D(rotationType3D);
        noise.SetFractalType(fractalType);
        noise.SetCellularDistanceFunction(cellularDistanceFunction);
        noise.SetCellularReturnType(cellularReturnType);
        noise.SetDomainWarpType(domainWarpType);
        return noise;
    }

    public void setInvert(boolean invert) {
        this.invert = invert;
    }

    public void SetSeed(long seed) {
        mSeed = seed;
    }

    public void SetFrequency(double frequency) {
        mFrequency = frequency;
    }

    public void SetNoiseType(NoiseType noiseType) {
        mNoiseType = noiseType;
        UpdateTransformType3D();
    }

    public void SetRotationType3D(RotationType3D rotationType3D) {
        mRotationType3D = rotationType3D;
        UpdateTransformType3D();
        UpdateWarpTransformType3D();
    }

    public void SetFractalType(FractalType fractalType) {
        mFractalType = fractalType;
    }

    public void SetFractalOctaves(int octaves) {
        mOctaves = octaves;
        CalculateFractalBounding();
    }

    public void SetFractalLacunarity(double lacunarity) {
        mLacunarity = lacunarity;
    }

    public void SetFractalGain(double gain) {
        mGain = gain;
        CalculateFractalBounding();
    }

    public void SetFractalWeightedStrength(double weightedStrength) {
        mWeightedStrength = weightedStrength;
    }

    public void SetFractalPingPongStrength(double pingPongStrength) {
        mPingPongStrength = pingPongStrength;
    }

    public void SetCellularDistanceFunction(CellularDistanceFunction cellularDistanceFunction) {
        mCellularDistanceFunction = cellularDistanceFunction;
    }

    public void SetCellularReturnType(CellularReturnType cellularReturnType) {
        mCellularReturnType = cellularReturnType;
    }

    public void SetCellularJitter(double cellularJitter) {
        mCellularJitterModifier = cellularJitter;
    }

    public void SetDomainWarpType(DomainWarpType domainWarpType) {
        mDomainWarpType = domainWarpType;
        UpdateWarpTransformType3D();
    }

    public void SetDomainWarpAmp(double domainWarpAmp) {
        mDomainWarpAmp = domainWarpAmp;
    }

    public double GetNoise(double x, double y, long eSeed) {

        x *= mFrequency;
        y *= mFrequency;

        switch (mNoiseType) {
            case OpenSimplex2:
            case OpenSimplex2S: {
                final double SQRT3 = (double) 1.7320508075688772935274463415059;
                final double F2 = 0.5d * (SQRT3 - 1);
                double t = (x + y) * F2;
                x += t;
                y += t;
            }
            break;
            default:
                break;
        }

        switch (mFractalType) {
            default:
                return invert ? -GenNoiseSingle(mSeed + eSeed, x, y) : GenNoiseSingle(mSeed + eSeed, x, y);
            case FBm:
                return invert ? -GenFractalFBm(x, y, eSeed) : GenFractalFBm(x, y, eSeed);
            case Ridged:
                return invert ? -GenFractalRidged(x, y, eSeed) : GenFractalRidged(x, y, eSeed);
            case PingPong:
                return invert ? -GenFractalPingPong(x, y, eSeed) : GenFractalPingPong(x, y, eSeed);
        }
    }

    public double GetNoise(double x, double y, double z, long eSeed) {
        x *= mFrequency;
        y *= mFrequency;
        z *= mFrequency;

        switch (mTransformType3D) {
            case ImproveXYPlanes: {
                double xy = x + y;
                double s2 = xy * -(double) 0.211324865405187;
                z *= (double) 0.577350269189626;
                x += s2 - z;
                y = y + s2 - z;
                z += xy * (double) 0.577350269189626;
            }
            break;
            case ImproveXZPlanes: {
                double xz = x + z;
                double s2 = xz * -(double) 0.211324865405187;
                y *= (double) 0.577350269189626;
                x += s2 - y;
                z += s2 - y;
                y += xz * (double) 0.577350269189626;
            }
            break;
            case DefaultOpenSimplex2: {
                final double R3 = (double) (2.0 / 3.0);
                double r = (x + y + z) * R3;
                x = r - x;
                y = r - y;
                z = r - z;
            }
            break;
            default:
                break;
        }

        switch (mFractalType) {
            default:
                return invert ? -GenNoiseSingle(mSeed + eSeed, x, y, z) : GenNoiseSingle(mSeed + eSeed, x, y, z);
            case FBm:
                return invert ? -GenFractalFBm(x, y, z, eSeed) : GenFractalFBm(x, y, z, eSeed);
            case Ridged:
                return invert ? -GenFractalRidged(x, y, z, eSeed) : GenFractalRidged(x, y, z, eSeed);
            case PingPong:
                return invert ? -GenFractalPingPong(x, y, z, eSeed) : GenFractalPingPong(x, y, z, eSeed);
        }
    }

    public void DomainWarp(Vector2 coord, long eSeed) {
        switch (mFractalType) {
            default:
                DomainWarpSingle(coord, eSeed);
                break;
            case DomainWarpProgressive:
                DomainWarpFractalProgressive(coord, eSeed);
                break;
            case DomainWarpIndependent:
                DomainWarpFractalIndependent(coord, eSeed);
                break;
        }
    }

    public void DomainWarp(Vector3 coord, long eSeed) {
        switch (mFractalType) {
            default:
                DomainWarpSingle(coord, eSeed);
                break;
            case DomainWarpProgressive:
                DomainWarpFractalProgressive(coord, eSeed);
                break;
            case DomainWarpIndependent:
                DomainWarpFractalIndependent(coord, eSeed);
                break;
        }
    }

    private static final double[] Gradients2D = { 0.130526192220052d, 0.99144486137381d, 0.38268343236509d, 0.923879532511287d, 0.608761429008721d, 0.793353340291235d, 0.793353340291235d, 0.608761429008721d, 0.923879532511287d, 0.38268343236509d, 0.99144486137381d, 0.130526192220051d, 0.99144486137381d, -0.130526192220051d, 0.923879532511287d, -0.38268343236509d, 0.793353340291235d, -0.60876142900872d, 0.608761429008721d, -0.793353340291235d, 0.38268343236509d, -0.923879532511287d, 0.130526192220052d, -0.99144486137381d, -0.130526192220052d, -0.99144486137381d, -0.38268343236509d, -0.923879532511287d, -0.608761429008721d, -0.793353340291235d, -0.793353340291235d, -0.608761429008721d, -0.923879532511287d, -0.38268343236509d, -0.99144486137381d, -0.130526192220052d, -0.99144486137381d, 0.130526192220051d, -0.923879532511287d, 0.38268343236509d, -0.793353340291235d, 0.608761429008721d, -0.608761429008721d, 0.793353340291235d, -0.38268343236509d, 0.923879532511287d, -0.130526192220052d, 0.99144486137381d, 0.130526192220052d, 0.99144486137381d, 0.38268343236509d, 0.923879532511287d, 0.608761429008721d, 0.793353340291235d, 0.793353340291235d, 0.608761429008721d, 0.923879532511287d, 0.38268343236509d, 0.99144486137381d, 0.130526192220051d, 0.99144486137381d, -0.130526192220051d, 0.923879532511287d, -0.38268343236509d, 0.793353340291235d, -0.60876142900872d, 0.608761429008721d, -0.793353340291235d, 0.38268343236509d, -0.923879532511287d, 0.130526192220052d, -0.99144486137381d, -0.130526192220052d, -0.99144486137381d, -0.38268343236509d, -0.923879532511287d, -0.608761429008721d, -0.793353340291235d, -0.793353340291235d, -0.608761429008721d, -0.923879532511287d, -0.38268343236509d, -0.99144486137381d, -0.130526192220052d, -0.99144486137381d, 0.130526192220051d, -0.923879532511287d, 0.38268343236509d, -0.793353340291235d, 0.608761429008721d, -0.608761429008721d, 0.793353340291235d, -0.38268343236509d, 0.923879532511287d, -0.130526192220052d, 0.99144486137381d, 0.130526192220052d, 0.99144486137381d, 0.38268343236509d, 0.923879532511287d, 0.608761429008721d, 0.793353340291235d, 0.793353340291235d, 0.608761429008721d, 0.923879532511287d, 0.38268343236509d, 0.99144486137381d, 0.130526192220051d, 0.99144486137381d, -0.130526192220051d, 0.923879532511287d, -0.38268343236509d, 0.793353340291235d, -0.60876142900872d, 0.608761429008721d, -0.793353340291235d, 0.38268343236509d, -0.923879532511287d, 0.130526192220052d, -0.99144486137381d, -0.130526192220052d, -0.99144486137381d, -0.38268343236509d, -0.923879532511287d, -0.608761429008721d, -0.793353340291235d, -0.793353340291235d, -0.608761429008721d, -0.923879532511287d, -0.38268343236509d, -0.99144486137381d, -0.130526192220052d, -0.99144486137381d, 0.130526192220051d, -0.923879532511287d, 0.38268343236509d, -0.793353340291235d, 0.608761429008721d, -0.608761429008721d, 0.793353340291235d, -0.38268343236509d, 0.923879532511287d, -0.130526192220052d, 0.99144486137381d, 0.130526192220052d, 0.99144486137381d, 0.38268343236509d, 0.923879532511287d, 0.608761429008721d, 0.793353340291235d, 0.793353340291235d, 0.608761429008721d, 0.923879532511287d, 0.38268343236509d, 0.99144486137381d, 0.130526192220051d, 0.99144486137381d, -0.130526192220051d, 0.923879532511287d, -0.38268343236509d, 0.793353340291235d, -0.60876142900872d, 0.608761429008721d, -0.793353340291235d, 0.38268343236509d, -0.923879532511287d, 0.130526192220052d, -0.99144486137381d, -0.130526192220052d, -0.99144486137381d, -0.38268343236509d, -0.923879532511287d, -0.608761429008721d, -0.793353340291235d, -0.793353340291235d, -0.608761429008721d, -0.923879532511287d, -0.38268343236509d, -0.99144486137381d, -0.130526192220052d, -0.99144486137381d, 0.130526192220051d, -0.923879532511287d, 0.38268343236509d, -0.793353340291235d, 0.608761429008721d, -0.608761429008721d, 0.793353340291235d, -0.38268343236509d, 0.923879532511287d, -0.130526192220052d, 0.99144486137381d, 0.130526192220052d, 0.99144486137381d, 0.38268343236509d, 0.923879532511287d, 0.608761429008721d, 0.793353340291235d, 0.793353340291235d, 0.608761429008721d, 0.923879532511287d, 0.38268343236509d, 0.99144486137381d, 0.130526192220051d, 0.99144486137381d, -0.130526192220051d, 0.923879532511287d, -0.38268343236509d, 0.793353340291235d, -0.60876142900872d, 0.608761429008721d, -0.793353340291235d, 0.38268343236509d, -0.923879532511287d, 0.130526192220052d, -0.99144486137381d, -0.130526192220052d, -0.99144486137381d, -0.38268343236509d, -0.923879532511287d, -0.608761429008721d, -0.793353340291235d, -0.793353340291235d, -0.608761429008721d, -0.923879532511287d, -0.38268343236509d, -0.99144486137381d, -0.130526192220052d, -0.99144486137381d, 0.130526192220051d, -0.923879532511287d, 0.38268343236509d, -0.793353340291235d, 0.608761429008721d, -0.608761429008721d, 0.793353340291235d, -0.38268343236509d, 0.923879532511287d, -0.130526192220052d, 0.99144486137381d, 0.38268343236509d, 0.923879532511287d, 0.923879532511287d, 0.38268343236509d, 0.923879532511287d, -0.38268343236509d, 0.38268343236509d, -0.923879532511287d, -0.38268343236509d, -0.923879532511287d, -0.923879532511287d, -0.38268343236509d, -0.923879532511287d, 0.38268343236509d, -0.38268343236509d, 0.923879532511287d, };
    private static final double[] RandVecs2D = { -0.2700222198d, -0.9628540911d, 0.3863092627d, -0.9223693152d, 0.04444859006d, -0.999011673d, -0.5992523158d, -0.8005602176d, -0.7819280288d, 0.6233687174d, 0.9464672271d, 0.3227999196d, -0.6514146797d, -0.7587218957d, 0.9378472289d, 0.347048376d, -0.8497875957d, -0.5271252623d, -0.879042592d, 0.4767432447d, -0.892300288d, -0.4514423508d, -0.379844434d, -0.9250503802d, -0.9951650832d, 0.0982163789d, 0.7724397808d, -0.6350880136d, 0.7573283322d, -0.6530343002d, -0.9928004525d, -0.119780055d, -0.0532665713d, 0.9985803285d, 0.9754253726d, -0.2203300762d, -0.7665018163d, 0.6422421394d, 0.991636706d, 0.1290606184d, -0.994696838d, 0.1028503788d, -0.5379205513d, -0.84299554d, 0.5022815471d, -0.8647041387d, 0.4559821461d, -0.8899889226d, -0.8659131224d, -0.5001944266d, 0.0879458407d, -0.9961252577d, -0.5051684983d, 0.8630207346d, 0.7753185226d, -0.6315704146d, -0.6921944612d, 0.7217110418d, -0.5191659449d, -0.8546734591d, 0.8978622882d, -0.4402764035d, -0.1706774107d, 0.9853269617d, -0.9353430106d, -0.3537420705d, -0.9992404798d, 0.03896746794d, -0.2882064021d, -0.9575683108d, -0.9663811329d, 0.2571137995d, -0.8759714238d, -0.4823630009d, -0.8303123018d, -0.5572983775d, 0.05110133755d, -0.9986934731d, -0.8558373281d, -0.5172450752d, 0.09887025282d, 0.9951003332d, 0.9189016087d, 0.3944867976d, -0.2439375892d, -0.9697909324d, -0.8121409387d, -0.5834613061d, -0.9910431363d, 0.1335421355d, 0.8492423985d, -0.5280031709d, -0.9717838994d, -0.2358729591d, 0.9949457207d, 0.1004142068d, 0.6241065508d, -0.7813392434d, 0.662910307d, 0.7486988212d, -0.7197418176d, 0.6942418282d, -0.8143370775d, -0.5803922158d, 0.104521054d, -0.9945226741d, -0.1065926113d, -0.9943027784d, 0.445799684d, -0.8951327509d, 0.105547406d, 0.9944142724d, -0.992790267d, 0.1198644477d, -0.8334366408d, 0.552615025d, 0.9115561563d, -0.4111755999d, 0.8285544909d, -0.5599084351d, 0.7217097654d, -0.6921957921d, 0.4940492677d, -0.8694339084d, -0.3652321272d, -0.9309164803d, -0.9696606758d, 0.2444548501d, 0.08925509731d, -0.996008799d, 0.5354071276d, -0.8445941083d, -0.1053576186d, 0.9944343981d, -0.9890284586d, 0.1477251101d, 0.004856104961d, 0.9999882091d, 0.9885598478d, 0.1508291331d, 0.9286129562d, -0.3710498316d, -0.5832393863d, -0.8123003252d, 0.3015207509d, 0.9534596146d, -0.9575110528d, 0.2883965738d, 0.9715802154d, -0.2367105511d, 0.229981792d, 0.9731949318d, 0.955763816d, -0.2941352207d, 0.740956116d, 0.6715534485d, -0.9971513787d, -0.07542630764d, 0.6905710663d, -0.7232645452d, -0.290713703d, -0.9568100872d, 0.5912777791d, -0.8064679708d, -0.9454592212d, -0.325740481d, 0.6664455681d, 0.74555369d, 0.6236134912d, 0.7817328275d, 0.9126993851d, -0.4086316587d, -0.8191762011d, 0.5735419353d, -0.8812745759d, -0.4726046147d, 0.9953313627d, 0.09651672651d, 0.9855650846d, -0.1692969699d, -0.8495980887d, 0.5274306472d, 0.6174853946d, -0.7865823463d, 0.8508156371d, 0.52546432d, 0.9985032451d, -0.05469249926d, 0.1971371563d, -0.9803759185d, 0.6607855748d, -0.7505747292d, -0.03097494063d, 0.9995201614d, -0.6731660801d, 0.739491331d, -0.7195018362d, -0.6944905383d, 0.9727511689d, 0.2318515979d, 0.9997059088d, -0.0242506907d, 0.4421787429d, -0.8969269532d, 0.9981350961d, -0.061043673d, -0.9173660799d, -0.3980445648d, -0.8150056635d, -0.5794529907d, -0.8789331304d, 0.4769450202d, 0.0158605829d, 0.999874213d, -0.8095464474d, 0.5870558317d, -0.9165898907d, -0.3998286786d, -0.8023542565d, 0.5968480938d, -0.5176737917d, 0.8555780767d, -0.8154407307d, -0.5788405779d, 0.4022010347d, -0.9155513791d, -0.9052556868d, -0.4248672045d, 0.7317445619d, 0.6815789728d, -0.5647632201d, -0.8252529947d, -0.8403276335d, -0.5420788397d, -0.9314281527d, 0.363925262d, 0.5238198472d, 0.8518290719d, 0.7432803869d, -0.6689800195d, -0.985371561d, -0.1704197369d, 0.4601468731d, 0.88784281d, 0.825855404d, 0.5638819483d, 0.6182366099d, 0.7859920446d, 0.8331502863d, -0.553046653d, 0.1500307506d, 0.9886813308d, -0.662330369d, -0.7492119075d, -0.668598664d, 0.743623444d, 0.7025606278d, 0.7116238924d, -0.5419389763d, -0.8404178401d, -0.3388616456d, 0.9408362159d, 0.8331530315d, 0.5530425174d, -0.2989720662d, -0.9542618632d, 0.2638522993d, 0.9645630949d, 0.124108739d, -0.9922686234d, -0.7282649308d, -0.6852956957d, 0.6962500149d, 0.7177993569d, -0.9183535368d, 0.3957610156d, -0.6326102274d, -0.7744703352d, -0.9331891859d, -0.359385508d, -0.1153779357d, -0.9933216659d, 0.9514974788d, -0.3076565421d, -0.08987977445d, -0.9959526224d, 0.6678496916d, 0.7442961705d, 0.7952400393d, -0.6062947138d, -0.6462007402d, -0.7631674805d, -0.2733598753d, 0.9619118351d, 0.9669590226d, -0.254931851d, -0.9792894595d, 0.2024651934d, -0.5369502995d, -0.8436138784d, -0.270036471d, -0.9628500944d, -0.6400277131d, 0.7683518247d, -0.7854537493d, -0.6189203566d, 0.06005905383d, -0.9981948257d, -0.02455770378d, 0.9996984141d, -0.65983623d, 0.751409442d, -0.6253894466d, -0.7803127835d, -0.6210408851d, -0.7837781695d, 0.8348888491d, 0.5504185768d, -0.1592275245d, 0.9872419133d, 0.8367622488d, 0.5475663786d, -0.8675753916d, -0.4973056806d, -0.2022662628d, -0.9793305667d, 0.9399189937d, 0.3413975472d, 0.9877404807d, -0.1561049093d, -0.9034455656d, 0.4287028224d, 0.1269804218d, -0.9919052235d, -0.3819600854d, 0.924178821d, 0.9754625894d, 0.2201652486d, -0.3204015856d, -0.9472818081d, -0.9874760884d, 0.1577687387d, 0.02535348474d, -0.9996785487d, 0.4835130794d, -0.8753371362d, -0.2850799925d, -0.9585037287d, -0.06805516006d, -0.99768156d, -0.7885244045d, -0.6150034663d, 0.3185392127d, -0.9479096845d, 0.8880043089d, 0.4598351306d, 0.6476921488d, -0.7619021462d, 0.9820241299d, 0.1887554194d, 0.9357275128d, -0.3527237187d, -0.8894895414d, 0.4569555293d, 0.7922791302d, 0.6101588153d, 0.7483818261d, 0.6632681526d, -0.7288929755d, -0.6846276581d, 0.8729032783d, -0.4878932944d, 0.8288345784d, 0.5594937369d, 0.08074567077d, 0.9967347374d, 0.9799148216d, -0.1994165048d, -0.580730673d, -0.8140957471d, -0.4700049791d, -0.8826637636d, 0.2409492979d, 0.9705377045d, 0.9437816757d, -0.3305694308d, -0.8927998638d, -0.4504535528d, -0.8069622304d, 0.5906030467d, 0.06258973166d, 0.9980393407d, -0.9312597469d, 0.3643559849d, 0.5777449785d, 0.8162173362d, -0.3360095855d, -0.941858566d, 0.697932075d, -0.7161639607d, -0.002008157227d, -0.9999979837d, -0.1827294312d, -0.9831632392d, -0.6523911722d, 0.7578824173d, -0.4302626911d, -0.9027037258d, -0.9985126289d, -0.05452091251d, -0.01028102172d, -0.9999471489d, -0.4946071129d, 0.8691166802d, -0.2999350194d, 0.9539596344d, 0.8165471961d, 0.5772786819d, 0.2697460475d, 0.962931498d, -0.7306287391d, -0.6827749597d, -0.7590952064d, -0.6509796216d, -0.907053853d, 0.4210146171d, -0.5104861064d, -0.8598860013d, 0.8613350597d, 0.5080373165d, 0.5007881595d, -0.8655698812d, -0.654158152d, 0.7563577938d, -0.8382755311d, -0.545246856d, 0.6940070834d, 0.7199681717d, 0.06950936031d, 0.9975812994d, 0.1702942185d, -0.9853932612d, 0.2695973274d, 0.9629731466d, 0.5519612192d, -0.8338697815d, 0.225657487d, -0.9742067022d, 0.4215262855d, -0.9068161835d, 0.4881873305d, -0.8727388672d, -0.3683854996d, -0.9296731273d, -0.9825390578d, 0.1860564427d, 0.81256471d, 0.5828709909d, 0.3196460933d, -0.9475370046d, 0.9570913859d, 0.2897862643d, -0.6876655497d, -0.7260276109d, -0.9988770922d, -0.047376731d, -0.1250179027d, 0.992154486d, -0.8280133617d, 0.560708367d, 0.9324863769d, -0.3612051451d, 0.6394653183d, 0.7688199442d, -0.01623847064d, -0.9998681473d, -0.9955014666d, -0.09474613458d, -0.81453315d, 0.580117012d, 0.4037327978d, -0.9148769469d, 0.9944263371d, 0.1054336766d, -0.1624711654d, 0.9867132919d, -0.9949487814d, -0.100383875d, -0.6995302564d, 0.7146029809d, 0.5263414922d, -0.85027327d, -0.5395221479d, 0.841971408d, 0.6579370318d, 0.7530729462d, 0.01426758847d, -0.9998982128d, -0.6734383991d, 0.7392433447d, 0.639412098d, -0.7688642071d, 0.9211571421d, 0.3891908523d, -0.146637214d, -0.9891903394d, -0.782318098d, 0.6228791163d, -0.5039610839d, -0.8637263605d, -0.7743120191d, -0.6328039957d, };
    private static final double[] Gradients3D = { 0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 1, 0, 1, 0, -1, 0, 1, 0, 1, 0, -1, 0, -1, 0, -1, 0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 0, 0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 1, 0, 1, 0, -1, 0, 1, 0, 1, 0, -1, 0, -1, 0, -1, 0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 0, 0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 1, 0, 1, 0, -1, 0, 1, 0, 1, 0, -1, 0, -1, 0, -1, 0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 0, 0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 1, 0, 1, 0, -1, 0, 1, 0, 1, 0, -1, 0, -1, 0, -1, 0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 0, 0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 1, 0, 1, 0, -1, 0, 1, 0, 1, 0, -1, 0, -1, 0, -1, 0, 1, 1, 0, 0, -1, 1, 0, 0, 1, -1, 0, 0, -1, -1, 0, 0, 1, 1, 0, 0, 0, -1, 1, 0, -1, 1, 0, 0, 0, -1, -1, 0 };
    private static final double[] RandVecs3D = { -0.7292736885d, -0.6618439697d, 0.1735581948d, 0, 0.790292081d, -0.5480887466d, -0.2739291014d, 0, 0.7217578935d, 0.6226212466d, -0.3023380997d, 0, 0.565683137d, -0.8208298145d, -0.0790000257d, 0, 0.760049034d, -0.5555979497d, -0.3370999617d, 0, 0.3713945616d, 0.5011264475d, 0.7816254623d, 0, -0.1277062463d, -0.4254438999d, -0.8959289049d, 0, -0.2881560924d, -0.5815838982d, 0.7607405838d, 0, 0.5849561111d, -0.662820239d, -0.4674352136d, 0, 0.3307171178d, 0.0391653737d, 0.94291689d, 0, 0.8712121778d, -0.4113374369d, -0.2679381538d, 0, 0.580981015d, 0.7021915846d, 0.4115677815d, 0, 0.503756873d, 0.6330056931d, -0.5878203852d, 0, 0.4493712205d, 0.601390195d, 0.6606022552d, 0, -0.6878403724d, 0.09018890807d, -0.7202371714d, 0, -0.5958956522d, -0.6469350577d, 0.475797649d, 0, -0.5127052122d, 0.1946921978d, -0.8361987284d, 0, -0.9911507142d, -0.05410276466d, -0.1212153153d, 0, -0.2149721042d, 0.9720882117d, -0.09397607749d, 0, -0.7518650936d, -0.5428057603d, 0.3742469607d, 0, 0.5237068895d, 0.8516377189d, -0.02107817834d, 0, 0.6333504779d, 0.1926167129d, -0.7495104896d, 0, -0.06788241606d, 0.3998305789d, 0.9140719259d, 0, -0.5538628599d, -0.4729896695d, -0.6852128902d, 0, -0.7261455366d, -0.5911990757d, 0.3509933228d, 0, -0.9229274737d, -0.1782808786d, 0.3412049336d, 0, -0.6968815002d, 0.6511274338d, 0.3006480328d, 0, 0.9608044783d, -0.2098363234d, -0.1811724921d, 0, 0.06817146062d, -0.9743405129d, 0.2145069156d, 0, -0.3577285196d, -0.6697087264d, -0.6507845481d, 0, -0.1868621131d, 0.7648617052d, -0.6164974636d, 0, -0.6541697588d, 0.3967914832d, 0.6439087246d, 0, 0.6993340405d, -0.6164538506d, 0.3618239211d, 0, -0.1546665739d, 0.6291283928d, 0.7617583057d, 0, -0.6841612949d, -0.2580482182d, -0.6821542638d, 0, 0.5383980957d, 0.4258654885d, 0.7271630328d, 0, -0.5026987823d, -0.7939832935d, -0.3418836993d, 0, 0.3202971715d, 0.2834415347d, 0.9039195862d, 0, 0.8683227101d, -0.0003762656404d, -0.4959995258d, 0, 0.791120031d, -0.08511045745d, 0.6057105799d, 0, -0.04011016052d, -0.4397248749d, 0.8972364289d, 0, 0.9145119872d, 0.3579346169d, -0.1885487608d, 0, -0.9612039066d, -0.2756484276d, 0.01024666929d, 0, 0.6510361721d, -0.2877799159d, -0.7023778346d, 0, -0.2041786351d, 0.7365237271d, 0.644859585d, 0, -0.7718263711d, 0.3790626912d, 0.5104855816d, 0, -0.3060082741d, -0.7692987727d, 0.5608371729d, 0, 0.454007341d, -0.5024843065d, 0.7357899537d, 0, 0.4816795475d, 0.6021208291d, -0.6367380315d, 0, 0.6961980369d, -0.3222197429d, 0.641469197d, 0, -0.6532160499d, -0.6781148932d, 0.3368515753d, 0, 0.5089301236d, -0.6154662304d, -0.6018234363d, 0, -0.1635919754d, -0.9133604627d, -0.372840892d, 0, 0.52408019d, -0.8437664109d, 0.1157505864d, 0, 0.5902587356d, 0.4983817807d, -0.6349883666d, 0, 0.5863227872d, 0.494764745d, 0.6414307729d, 0, 0.6779335087d, 0.2341345225d, 0.6968408593d, 0, 0.7177054546d, -0.6858979348d, 0.120178631d, 0, -0.5328819713d, -0.5205125012d, 0.6671608058d, 0, -0.8654874251d, -0.0700727088d, -0.4960053754d, 0, -0.2861810166d, 0.7952089234d, 0.5345495242d, 0, -0.04849529634d, 0.9810836427d, -0.1874115585d, 0, -0.6358521667d, 0.6058348682d, 0.4781800233d, 0, 0.6254794696d, -0.2861619734d, 0.7258696564d, 0, -0.2585259868d, 0.5061949264d, -0.8227581726d, 0, 0.02136306781d, 0.5064016808d, -0.8620330371d, 0, 0.200111773d, 0.8599263484d, 0.4695550591d, 0, 0.4743561372d, 0.6014985084d, -0.6427953014d, 0, 0.6622993731d, -0.5202474575d, -0.5391679918d, 0, 0.08084972818d, -0.6532720452d, 0.7527940996d, 0, -0.6893687501d, 0.0592860349d, 0.7219805347d, 0, -0.1121887082d, -0.9673185067d, 0.2273952515d, 0, 0.7344116094d, 0.5979668656d, -0.3210532909d, 0, 0.5789393465d, -0.2488849713d, 0.7764570201d, 0, 0.6988182827d, 0.3557169806d, -0.6205791146d, 0, -0.8636845529d, -0.2748771249d, -0.4224826141d, 0, -0.4247027957d, -0.4640880967d, 0.777335046d, 0, 0.5257722489d, -0.8427017621d, 0.1158329937d, 0, 0.9343830603d, 0.316302472d, -0.1639543925d, 0, -0.1016836419d, -0.8057303073d, -0.5834887393d, 0, -0.6529238969d, 0.50602126d, -0.5635892736d, 0, -0.2465286165d, -0.9668205684d, -0.06694497494d, 0, -0.9776897119d, -0.2099250524d, -0.007368825344d, 0, 0.7736893337d, 0.5734244712d, 0.2694238123d, 0, -0.6095087895d, 0.4995678998d, 0.6155736747d, 0, 0.5794535482d, 0.7434546771d, 0.3339292269d, 0, -0.8226211154d, 0.08142581855d, 0.5627293636d, 0, -0.510385483d, 0.4703667658d, 0.7199039967d, 0, -0.5764971849d, -0.07231656274d, -0.8138926898d, 0, 0.7250628871d, 0.3949971505d, -0.5641463116d, 0, -0.1525424005d, 0.4860840828d, -0.8604958341d, 0, -0.5550976208d, -0.4957820792d, 0.667882296d, 0, -0.1883614327d, 0.9145869398d, 0.357841725d, 0, 0.7625556724d, -0.5414408243d, -0.3540489801d, 0, -0.5870231946d, -0.3226498013d, -0.7424963803d, 0, 0.3051124198d, 0.2262544068d, -0.9250488391d, 0, 0.6379576059d, 0.577242424d, -0.5097070502d, 0, -0.5966775796d, 0.1454852398d, -0.7891830656d, 0, -0.658330573d, 0.6555487542d, -0.3699414651d, 0, 0.7434892426d, 0.2351084581d, 0.6260573129d, 0, 0.5562114096d, 0.8264360377d, -0.0873632843d, 0, -0.3028940016d, -0.8251527185d, 0.4768419182d, 0, 0.1129343818d, -0.985888439d, -0.1235710781d, 0, 0.5937652891d, -0.5896813806d, 0.5474656618d, 0, 0.6757964092d, -0.5835758614d, -0.4502648413d, 0, 0.7242302609d, -0.1152719764d, 0.6798550586d, 0, -0.9511914166d, 0.0753623979d, -0.2992580792d, 0, 0.2539470961d, -0.1886339355d, 0.9486454084d, 0, 0.571433621d, -0.1679450851d, -0.8032795685d, 0, -0.06778234979d, 0.3978269256d, 0.9149531629d, 0, 0.6074972649d, 0.733060024d, -0.3058922593d, 0, -0.5435478392d, 0.1675822484d, 0.8224791405d, 0, -0.5876678086d, -0.3380045064d, -0.7351186982d, 0, -0.7967562402d, 0.04097822706d, -0.6029098428d, 0, -0.1996350917d, 0.8706294745d, 0.4496111079d, 0, -0.02787660336d, -0.9106232682d, -0.4122962022d, 0, -0.7797625996d, -0.6257634692d, 0.01975775581d, 0, -0.5211232846d, 0.7401644346d, -0.4249554471d, 0, 0.8575424857d, 0.4053272873d, -0.3167501783d, 0, 0.1045223322d, 0.8390195772d, -0.5339674439d, 0, 0.3501822831d, 0.9242524096d, -0.1520850155d, 0, 0.1987849858d, 0.07647613266d, 0.9770547224d, 0, 0.7845996363d, 0.6066256811d, -0.1280964233d, 0, 0.09006737436d, -0.9750989929d, -0.2026569073d, 0, -0.8274343547d, -0.542299559d, 0.1458203587d, 0, -0.3485797732d, -0.415802277d, 0.840000362d, 0, -0.2471778936d, -0.7304819962d, -0.6366310879d, 0, -0.3700154943d, 0.8577948156d, 0.3567584454d, 0, 0.5913394901d, -0.548311967d, -0.5913303597d, 0, 0.1204873514d, -0.7626472379d, -0.6354935001d, 0, 0.616959265d, 0.03079647928d, 0.7863922953d, 0, 0.1258156836d, -0.6640829889d, -0.7369967419d, 0, -0.6477565124d, -0.1740147258d, -0.7417077429d, 0, 0.6217889313d, -0.7804430448d, -0.06547655076d, 0, 0.6589943422d, -0.6096987708d, 0.4404473475d, 0, -0.2689837504d, -0.6732403169d, -0.6887635427d, 0, -0.3849775103d, 0.5676542638d, 0.7277093879d, 0, 0.5754444408d, 0.8110471154d, -0.1051963504d, 0, 0.9141593684d, 0.3832947817d, 0.131900567d, 0, -0.107925319d, 0.9245493968d, 0.3654593525d, 0, 0.377977089d, 0.3043148782d, 0.8743716458d, 0, -0.2142885215d, -0.8259286236d, 0.5214617324d, 0, 0.5802544474d, 0.4148098596d, -0.7008834116d, 0, -0.1982660881d, 0.8567161266d, -0.4761596756d, 0, -0.03381553704d, 0.3773180787d, -0.9254661404d, 0, -0.6867922841d, -0.6656597827d, 0.2919133642d, 0, 0.7731742607d, -0.2875793547d, -0.5652430251d, 0, -0.09655941928d, 0.9193708367d, -0.3813575004d, 0, 0.2715702457d, -0.9577909544d, -0.09426605581d, 0, 0.2451015704d, -0.6917998565d, -0.6792188003d, 0, 0.977700782d, -0.1753855374d, 0.1155036542d, 0, -0.5224739938d, 0.8521606816d, 0.02903615945d, 0, -0.7734880599d, -0.5261292347d, 0.3534179531d, 0, -0.7134492443d, -0.269547243d, 0.6467878011d, 0, 0.1644037271d, 0.5105846203d, -0.8439637196d, 0, 0.6494635788d, 0.05585611296d, 0.7583384168d, 0, -0.4711970882d, 0.5017280509d, -0.7254255765d, 0, -0.6335764307d, -0.2381686273d, -0.7361091029d, 0, -0.9021533097d, -0.270947803d, -0.3357181763d, 0, -0.3793711033d, 0.872258117d, 0.3086152025d, 0, -0.6855598966d, -0.3250143309d, 0.6514394162d, 0, 0.2900942212d, -0.7799057743d, -0.5546100667d, 0, -0.2098319339d, 0.85037073d, 0.4825351604d, 0, -0.4592603758d, 0.6598504336d, -0.5947077538d, 0, 0.8715945488d, 0.09616365406d, -0.4807031248d, 0, -0.6776666319d, 0.7118504878d, -0.1844907016d, 0, 0.7044377633d, 0.312427597d, 0.637304036d, 0, -0.7052318886d, -0.2401093292d, -0.6670798253d, 0, 0.081921007d, -0.7207336136d, -0.6883545647d, 0, -0.6993680906d, -0.5875763221d, -0.4069869034d, 0, -0.1281454481d, 0.6419895885d, 0.7559286424d, 0, -0.6337388239d, -0.6785471501d, -0.3714146849d, 0, 0.5565051903d, -0.2168887573d, -0.8020356851d, 0, -0.5791554484d, 0.7244372011d, -0.3738578718d, 0, 0.1175779076d, -0.7096451073d, 0.6946792478d, 0, -0.6134619607d, 0.1323631078d, 0.7785527795d, 0, 0.6984635305d, -0.02980516237d, -0.715024719d, 0, 0.8318082963d, -0.3930171956d, 0.3919597455d, 0, 0.1469576422d, 0.05541651717d, -0.9875892167d, 0, 0.708868575d, -0.2690503865d, 0.6520101478d, 0, 0.2726053183d, 0.67369766d, -0.68688995d, 0,
            -0.6591295371d, 0.3035458599d, -0.6880466294d, 0, 0.4815131379d, -0.7528270071d, 0.4487723203d, 0, 0.9430009463d, 0.1675647412d, -0.2875261255d, 0, 0.434802957d, 0.7695304522d, -0.4677277752d, 0, 0.3931996188d, 0.594473625d, 0.7014236729d, 0, 0.7254336655d, -0.603925654d, 0.3301814672d, 0, 0.7590235227d, -0.6506083235d, 0.02433313207d, 0, -0.8552768592d, -0.3430042733d, 0.3883935666d, 0, -0.6139746835d, 0.6981725247d, 0.3682257648d, 0, -0.7465905486d, -0.5752009504d, 0.3342849376d, 0, 0.5730065677d, 0.810555537d, -0.1210916791d, 0, -0.9225877367d, -0.3475211012d, -0.167514036d, 0, -0.7105816789d, -0.4719692027d, -0.5218416899d, 0, -0.08564609717d, 0.3583001386d, 0.929669703d, 0, -0.8279697606d, -0.2043157126d, 0.5222271202d, 0, 0.427944023d, 0.278165994d, 0.8599346446d, 0, 0.5399079671d, -0.7857120652d, -0.3019204161d, 0, 0.5678404253d, -0.5495413974d, -0.6128307303d, 0, -0.9896071041d, 0.1365639107d, -0.04503418428d, 0, -0.6154342638d, -0.6440875597d, 0.4543037336d, 0, 0.1074204368d, -0.7946340692d, 0.5975094525d, 0, -0.3595449969d, -0.8885529948d, 0.28495784d, 0, -0.2180405296d, 0.1529888965d, 0.9638738118d, 0, -0.7277432317d, -0.6164050508d, -0.3007234646d, 0, 0.7249729114d, -0.00669719484d, 0.6887448187d, 0, -0.5553659455d, -0.5336586252d, 0.6377908264d, 0, 0.5137558015d, 0.7976208196d, -0.3160000073d, 0, -0.3794024848d, 0.9245608561d, -0.03522751494d, 0, 0.8229248658d, 0.2745365933d, -0.4974176556d, 0, -0.5404114394d, 0.6091141441d, 0.5804613989d, 0, 0.8036581901d, -0.2703029469d, 0.5301601931d, 0, 0.6044318879d, 0.6832968393d, 0.4095943388d, 0, 0.06389988817d, 0.9658208605d, -0.2512108074d, 0, 0.1087113286d, 0.7402471173d, -0.6634877936d, 0, -0.713427712d, -0.6926784018d, 0.1059128479d, 0, 0.6458897819d, -0.5724548511d, -0.5050958653d, 0, -0.6553931414d, 0.7381471625d, 0.159995615d, 0, 0.3910961323d, 0.9188871375d, -0.05186755998d, 0, -0.4879022471d, -0.5904376907d, 0.6429111375d, 0, 0.6014790094d, 0.7707441366d, -0.2101820095d, 0, -0.5677173047d, 0.7511360995d, 0.3368851762d, 0, 0.7858573506d, 0.226674665d, 0.5753666838d, 0, -0.4520345543d, -0.604222686d, -0.6561857263d, 0, 0.002272116345d, 0.4132844051d, -0.9105991643d, 0, -0.5815751419d, -0.5162925989d, 0.6286591339d, 0, -0.03703704785d, 0.8273785755d, 0.5604221175d, 0, -0.5119692504d, 0.7953543429d, -0.3244980058d, 0, -0.2682417366d, -0.9572290247d, -0.1084387619d, 0, -0.2322482736d, -0.9679131102d, -0.09594243324d, 0, 0.3554328906d, -0.8881505545d, 0.2913006227d, 0, 0.7346520519d, -0.4371373164d, 0.5188422971d, 0, 0.9985120116d, 0.04659011161d, -0.02833944577d, 0, -0.3727687496d, -0.9082481361d, 0.1900757285d, 0, 0.91737377d, -0.3483642108d, 0.1925298489d, 0, 0.2714911074d, 0.4147529736d, -0.8684886582d, 0, 0.5131763485d, -0.7116334161d, 0.4798207128d, 0, -0.8737353606d, 0.18886992d, -0.4482350644d, 0, 0.8460043821d, -0.3725217914d, 0.3814499973d, 0, 0.8978727456d, -0.1780209141d, -0.4026575304d, 0, 0.2178065647d, -0.9698322841d, -0.1094789531d, 0, -0.1518031304d, -0.7788918132d, -0.6085091231d, 0, -0.2600384876d, -0.4755398075d, -0.8403819825d, 0, 0.572313509d, -0.7474340931d, -0.3373418503d, 0, -0.7174141009d, 0.1699017182d, -0.6756111411d, 0, -0.684180784d, 0.02145707593d, -0.7289967412d, 0, -0.2007447902d, 0.06555605789d, -0.9774476623d, 0, -0.1148803697d, -0.8044887315d, 0.5827524187d, 0, -0.7870349638d, 0.03447489231d, 0.6159443543d, 0, -0.2015596421d, 0.6859872284d, 0.6991389226d, 0, -0.08581082512d, -0.10920836d, -0.9903080513d, 0, 0.5532693395d, 0.7325250401d, -0.396610771d, 0, -0.1842489331d, -0.9777375055d, -0.1004076743d, 0, 0.0775473789d, -0.9111505856d, 0.4047110257d, 0, 0.1399838409d, 0.7601631212d, -0.6344734459d, 0, 0.4484419361d, -0.845289248d, 0.2904925424d, 0 };

    private static double FastMin(double a, double b) {
        return a < b ? a : b;
    }

    private static double FastMax(double a, double b) {
        return a > b ? a : b;
    }

    private static double FastAbs(double f) {
        return f < 0 ? -f : f;
    }

    private static double FastSqrt(double f) {
        return (double) Math.sqrt(f);
    }

    private static int FastFloor(double f) {
        return f >= 0 ? (int) f : (int) f - 1;
    }

    private static int FastRound(double f) {
        return f >= 0 ? (int) (f + 0.5d) : (int) (f - 0.5d);
    }

    private static double Lerp(double a, double b, double t) {
        return a + t * (b - a);
    }

    private static double InterpHermite(double t) {
        return t * t * (3 - 2 * t);
    }

    private static double InterpQuintic(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static double CubicLerp(double a, double b, double c, double d, double t) {
        double p = (d - c) - (a - b);
        return t * t * t * p + t * t * ((a - b) - p) + t * (c - a) + b;
    }

    private static double PingPong(double t) {
        t -= (int) (t * 0.5d) * 2;
        return t < 1 ? t : 2 - t;
    }

    private void CalculateFractalBounding() {
        double gain = FastAbs(mGain);
        double amp = gain;
        double ampFractal = 1.0d;
        for (int i = 1; i < mOctaves; i++) {
            ampFractal += amp;
            amp *= gain;
        }
        mFractalBounding = 1 / ampFractal;
    }

    private static final int PrimeX = 501125321;
    private static final int PrimeY = 1136930381;
    private static final int PrimeZ = 1720413743;

    private static int Hash(long seed, int xPrimed, int yPrimed) {
        long hash = seed ^ xPrimed ^ yPrimed;

        hash *= 0x27d4eb2d;
        return (int) hash;
    }

    private static int Hash(long seed, int xPrimed, int yPrimed, int zPrimed) {
        long hash = seed ^ xPrimed ^ yPrimed ^ zPrimed;

        hash *= 0x27d4eb2d;
        return (int) hash;
    }

    private static double ValCoord(long seed, int xPrimed, int yPrimed) {
        long hash = Hash(seed, xPrimed, yPrimed);

        hash *= hash;
        hash ^= hash << 19;
        return hash * (1 / 2147483648.0d);
    }

    private static double ValCoord(long seed, int xPrimed, int yPrimed, int zPrimed) {
        long hash = Hash(seed, xPrimed, yPrimed, zPrimed);

        hash *= hash;
        hash ^= hash << 19;
        return hash * (1 / 2147483648.0d);
    }

    private static double GradCoord(long seed, int xPrimed, int yPrimed, double xd, double yd) {
        int hash = Hash(seed, xPrimed, yPrimed);
        hash ^= hash >> 15;
        hash &= 127 << 1;

        double xg = Gradients2D[hash];
        double yg = Gradients2D[hash | 1];

        return xd * xg + yd * yg;
    }

    private static double GradCoord(long seed, int xPrimed, int yPrimed, int zPrimed, double xd, double yd, double zd) {
        int hash = Hash(seed, xPrimed, yPrimed, zPrimed);
        hash ^= hash >> 15;
        hash &= 63 << 2;

        double xg = Gradients3D[hash];
        double yg = Gradients3D[hash | 1];
        double zg = Gradients3D[hash | 2];

        return xd * xg + yd * yg + zd * zg;
    }

    private double GenNoiseSingle(long seed, double x, double y) {
        switch (mNoiseType) {
            case OpenSimplex2:
                return SingleSimplex(seed, x, y);
            case OpenSimplex2S:
                return SingleOpenSimplex2S(seed, x, y);
            case Cellular:
                return SingleCellular(seed, x, y);
            case Perlin:
                return SinglePerlin(seed, x, y);
            case ValueCubic:
                return SingleValueCubic(seed, x, y);
            case Value:
                return SingleValue(seed, x, y);
            default:
                return 0;
        }
    }

    private double GenNoiseSingle(long seed, double x, double y, double z) {
        switch (mNoiseType) {
            case OpenSimplex2:
                return SingleOpenSimplex2(seed, x, y, z);
            case OpenSimplex2S:
                return SingleOpenSimplex2S(seed, x, y, z);
            case Cellular:
                return SingleCellular(seed, x, y, z);
            case Perlin:
                return SinglePerlin(seed, x, y, z);
            case ValueCubic:
                return SingleValueCubic(seed, x, y, z);
            case Value:
                return SingleValue(seed, x, y, z);
            default:
                return 0;
        }
    }

    private void UpdateTransformType3D() {
        switch (mRotationType3D) {
            case ImproveXYPlanes:
                mTransformType3D = TransformType3D.ImproveXYPlanes;
                break;
            case ImproveXZPlanes:
                mTransformType3D = TransformType3D.ImproveXZPlanes;
                break;
            default:
                switch (mNoiseType) {
                    case OpenSimplex2:
                    case OpenSimplex2S:
                        mTransformType3D = TransformType3D.DefaultOpenSimplex2;
                        break;
                    default:
                        mTransformType3D = TransformType3D.None;
                        break;
                }
                break;
        }
    }

    private void UpdateWarpTransformType3D() {
        switch (mRotationType3D) {
            case ImproveXYPlanes:
                mWarpTransformType3D = TransformType3D.ImproveXYPlanes;
                break;
            case ImproveXZPlanes:
                mWarpTransformType3D = TransformType3D.ImproveXZPlanes;
                break;
            default:
                switch (mDomainWarpType) {
                    case OpenSimplex2:
                    case OpenSimplex2Reduced:
                        mWarpTransformType3D = TransformType3D.DefaultOpenSimplex2;
                        break;
                    default:
                        mWarpTransformType3D = TransformType3D.None;
                        break;
                }
                break;
        }
    }

    private double GenFractalFBm(double x, double y, long eSeed) {
        long seed = mSeed + eSeed;
        double sum = 0;
        double amp = mFractalBounding;

        for (int i = 0; i < mOctaves; i++) {
            double noise = GenNoiseSingle(seed++, x, y);
            sum += noise * amp;
            amp *= Lerp(1.0d, FastMin(noise + 1, 2) * 0.5d, mWeightedStrength);

            x *= mLacunarity;
            y *= mLacunarity;
            amp *= mGain;
        }

        return sum;
    }

    private double GenFractalFBm(double x, double y, double z, long eSeed) {
        long seed = mSeed + eSeed;
        double sum = 0;
        double amp = mFractalBounding;

        for (int i = 0; i < mOctaves; i++) {
            double noise = GenNoiseSingle(seed++, x, y, z);
            sum += noise * amp;
            amp *= Lerp(1.0d, (noise + 1) * 0.5d, mWeightedStrength);

            x *= mLacunarity;
            y *= mLacunarity;
            z *= mLacunarity;
            amp *= mGain;
        }

        return sum;
    }

    private double GenFractalRidged(double x, double y, long eSeed) {
        long seed = mSeed;
        double sum = 0;
        double amp = mFractalBounding;

        for (int i = 0; i < mOctaves; i++) {
            double noise = FastAbs(GenNoiseSingle(seed++, x, y));
            sum += (noise * -2 + 1) * amp;
            amp *= Lerp(1.0d, 1 - noise, mWeightedStrength);

            x *= mLacunarity;
            y *= mLacunarity;
            amp *= mGain;
        }

        return sum;
    }

    private double GenFractalRidged(double x, double y, double z, long eSeed) {
        long seed = mSeed;
        double sum = 0;
        double amp = mFractalBounding;

        for (int i = 0; i < mOctaves; i++) {
            double noise = FastAbs(GenNoiseSingle(seed++, x, y, z));
            sum += (noise * -2 + 1) * amp;
            amp *= Lerp(1.0d, 1 - noise, mWeightedStrength);

            x *= mLacunarity;
            y *= mLacunarity;
            z *= mLacunarity;
            amp *= mGain;
        }

        return sum;
    }

    private double GenFractalPingPong(double x, double y, long eSeed) {
        long seed = mSeed;
        double sum = 0;
        double amp = mFractalBounding;

        for (int i = 0; i < mOctaves; i++) {
            double noise = PingPong((GenNoiseSingle(seed++, x, y) + 1) * mPingPongStrength);
            sum += (noise - 0.5d) * 2 * amp;
            amp *= Lerp(1.0d, noise, mWeightedStrength);

            x *= mLacunarity;
            y *= mLacunarity;
            amp *= mGain;
        }

        return sum;
    }

    private double GenFractalPingPong(double x, double y, double z, long eSeed) {
        long seed = mSeed;
        double sum = 0;
        double amp = mFractalBounding;

        for (int i = 0; i < mOctaves; i++) {
            double noise = PingPong((GenNoiseSingle(seed++, x, y, z) + 1) * mPingPongStrength);
            sum += (noise - 0.5d) * 2 * amp;
            amp *= Lerp(1.0d, noise, mWeightedStrength);

            x *= mLacunarity;
            y *= mLacunarity;
            z *= mLacunarity;
            amp *= mGain;
        }

        return sum;
    }

    private double SingleSimplex(long seed, double x, double y) {

        final double SQRT3 = 1.7320508075688772935274463415059d;
        final double G2 = (3 - SQRT3) / 6;

        int i = FastFloor(x);
        int j = FastFloor(y);
        double xi = (double) (x - i);
        double yi = (double) (y - j);

        double t = (xi + yi) * G2;
        double x0 = (double) (xi - t);
        double y0 = (double) (yi - t);

        i *= PrimeX;
        j *= PrimeY;

        double n0, n1, n2;

        double a = 0.5d - x0 * x0 - y0 * y0;
        if (a <= 0)
            n0 = 0;
        else {
            n0 = (a * a) * (a * a) * GradCoord(seed, i, j, x0, y0);
        }

        double c = (double) (2 * (1 - 2 * G2) * (1 / G2 - 2)) * t + ((double) (-2 * (1 - 2 * G2) * (1 - 2 * G2)) + a);
        if (c <= 0)
            n2 = 0;
        else {
            double x2 = x0 + (2 * (double) G2 - 1);
            double y2 = y0 + (2 * (double) G2 - 1);
            n2 = (c * c) * (c * c) * GradCoord(seed, i + PrimeX, j + PrimeY, x2, y2);
        }

        if (y0 > x0) {
            double x1 = x0 + (double) G2;
            double y1 = y0 + ((double) G2 - 1);
            double b = 0.5d - x1 * x1 - y1 * y1;
            if (b <= 0)
                n1 = 0;
            else {
                n1 = (b * b) * (b * b) * GradCoord(seed, i, j + PrimeY, x1, y1);
            }
        } else {
            double x1 = x0 + ((double) G2 - 1);
            double y1 = y0 + (double) G2;
            double b = 0.5d - x1 * x1 - y1 * y1;
            if (b <= 0)
                n1 = 0;
            else {
                n1 = (b * b) * (b * b) * GradCoord(seed, i + PrimeX, j, x1, y1);
            }
        }

        return (n0 + n1 + n2) * 99.83685446303647d;
    }

    private double SingleOpenSimplex2(long seed, double x, double y, double z) {

        int i = FastRound(x);
        int j = FastRound(y);
        int k = FastRound(z);
        double x0 = (double) (x - i);
        double y0 = (double) (y - j);
        double z0 = (double) (z - k);

        int xNSign = (int) (-1.0d - x0) | 1;
        int yNSign = (int) (-1.0d - y0) | 1;
        int zNSign = (int) (-1.0d - z0) | 1;

        double ax0 = xNSign * -x0;
        double ay0 = yNSign * -y0;
        double az0 = zNSign * -z0;

        i *= PrimeX;
        j *= PrimeY;
        k *= PrimeZ;

        double value = 0;
        double a = (0.6d - x0 * x0) - (y0 * y0 + z0 * z0);

        for (int l = 0;; l++) {
            if (a > 0) {
                value += (a * a) * (a * a) * GradCoord(seed, i, j, k, x0, y0, z0);
            }

            if (ax0 >= ay0 && ax0 >= az0) {
                double b = a + ax0 + ax0;
                if (b > 1) {
                    b -= 1;
                    value += (b * b) * (b * b) * GradCoord(seed, i - xNSign * PrimeX, j, k, x0 + xNSign, y0, z0);
                }
            } else if (ay0 > ax0 && ay0 >= az0) {
                double b = a + ay0 + ay0;
                if (b > 1) {
                    b -= 1;
                    value += (b * b) * (b * b) * GradCoord(seed, i, j - yNSign * PrimeY, k, x0, y0 + yNSign, z0);
                }
            } else {
                double b = a + az0 + az0;
                if (b > 1) {
                    b -= 1;
                    value += (b * b) * (b * b) * GradCoord(seed, i, j, k - zNSign * PrimeZ, x0, y0, z0 + zNSign);
                }
            }

            if (l == 1)
                break;

            ax0 = 0.5d - ax0;
            ay0 = 0.5d - ay0;
            az0 = 0.5d - az0;

            x0 = xNSign * ax0;
            y0 = yNSign * ay0;
            z0 = zNSign * az0;

            a += (0.75d - ax0) - (ay0 + az0);

            i += (xNSign >> 1) & PrimeX;
            j += (yNSign >> 1) & PrimeY;
            k += (zNSign >> 1) & PrimeZ;

            xNSign = -xNSign;
            yNSign = -yNSign;
            zNSign = -zNSign;

            seed = ~seed;
        }

        return value * 32.69428253173828125d;
    }

    private double SingleOpenSimplex2S(long seed, double x, double y) {

        final double SQRT3 = (double) 1.7320508075688772935274463415059;
        final double G2 = (3 - SQRT3) / 6;

        int i = FastFloor(x);
        int j = FastFloor(y);
        double xi = (double) (x - i);
        double yi = (double) (y - j);

        i *= PrimeX;
        j *= PrimeY;
        int i1 = i + PrimeX;
        int j1 = j + PrimeY;

        double t = (xi + yi) * (double) G2;
        double x0 = xi - t;
        double y0 = yi - t;

        double a0 = (2.0d / 3.0d) - x0 * x0 - y0 * y0;
        double value = (a0 * a0) * (a0 * a0) * GradCoord(seed, i, j, x0, y0);

        double a1 = (double) (2 * (1 - 2 * G2) * (1 / G2 - 2)) * t + ((double) (-2 * (1 - 2 * G2) * (1 - 2 * G2)) + a0);
        double x1 = x0 - (double) (1 - 2 * G2);
        double y1 = y0 - (double) (1 - 2 * G2);
        value += (a1 * a1) * (a1 * a1) * GradCoord(seed, i1, j1, x1, y1);

        double xmyi = xi - yi;
        if (t > G2) {
            if (xi + xmyi > 1) {
                double x2 = x0 + (double) (3 * G2 - 2);
                double y2 = y0 + (double) (3 * G2 - 1);
                double a2 = (2.0d / 3.0d) - x2 * x2 - y2 * y2;
                if (a2 > 0) {
                    value += (a2 * a2) * (a2 * a2) * GradCoord(seed, i + (PrimeX << 1), j + PrimeY, x2, y2);
                }
            } else {
                double x2 = x0 + (double) G2;
                double y2 = y0 + (double) (G2 - 1);
                double a2 = (2.0d / 3.0d) - x2 * x2 - y2 * y2;
                if (a2 > 0) {
                    value += (a2 * a2) * (a2 * a2) * GradCoord(seed, i, j + PrimeY, x2, y2);
                }
            }

            if (yi - xmyi > 1) {
                double x3 = x0 + (double) (3 * G2 - 1);
                double y3 = y0 + (double) (3 * G2 - 2);
                double a3 = (2.0d / 3.0d) - x3 * x3 - y3 * y3;
                if (a3 > 0) {
                    value += (a3 * a3) * (a3 * a3) * GradCoord(seed, i + PrimeX, j + (PrimeY << 1), x3, y3);
                }
            } else {
                double x3 = x0 + (double) (G2 - 1);
                double y3 = y0 + (double) G2;
                double a3 = (2.0d / 3.0d) - x3 * x3 - y3 * y3;
                if (a3 > 0) {
                    value += (a3 * a3) * (a3 * a3) * GradCoord(seed, i + PrimeX, j, x3, y3);
                }
            }
        } else {
            if (xi + xmyi < 0) {
                double x2 = x0 + (double) (1 - G2);
                double y2 = y0 - (double) G2;
                double a2 = (2.0d / 3.0d) - x2 * x2 - y2 * y2;
                if (a2 > 0) {
                    value += (a2 * a2) * (a2 * a2) * GradCoord(seed, i - PrimeX, j, x2, y2);
                }
            } else {
                double x2 = x0 + (double) (G2 - 1);
                double y2 = y0 + (double) G2;
                double a2 = (2.0d / 3.0d) - x2 * x2 - y2 * y2;
                if (a2 > 0) {
                    value += (a2 * a2) * (a2 * a2) * GradCoord(seed, i + PrimeX, j, x2, y2);
                }
            }

            if (yi < xmyi) {
                double x2 = x0 - (double) G2;
                double y2 = y0 - (double) (G2 - 1);
                double a2 = (2.0d / 3.0d) - x2 * x2 - y2 * y2;
                if (a2 > 0) {
                    value += (a2 * a2) * (a2 * a2) * GradCoord(seed, i, j - PrimeY, x2, y2);
                }
            } else {
                double x2 = x0 + (double) G2;
                double y2 = y0 + (double) (G2 - 1);
                double a2 = (2.0d / 3.0d) - x2 * x2 - y2 * y2;
                if (a2 > 0) {
                    value += (a2 * a2) * (a2 * a2) * GradCoord(seed, i, j + PrimeY, x2, y2);
                }
            }
        }

        return value * 18.24196194486065d;
    }

    private double SingleOpenSimplex2S(long seed, double x, double y, double z) {

        int i = FastFloor(x);
        int j = FastFloor(y);
        int k = FastFloor(z);
        double xi = (double) (x - i);
        double yi = (double) (y - j);
        double zi = (double) (z - k);

        i *= PrimeX;
        j *= PrimeY;
        k *= PrimeZ;
        long seed2 = seed + 1293373;

        int xNMask = (int) (-0.5d - xi);
        int yNMask = (int) (-0.5d - yi);
        int zNMask = (int) (-0.5d - zi);

        double x0 = xi + xNMask;
        double y0 = yi + yNMask;
        double z0 = zi + zNMask;
        double a0 = 0.75d - x0 * x0 - y0 * y0 - z0 * z0;
        double value = (a0 * a0) * (a0 * a0) * GradCoord(seed, i + (xNMask & PrimeX), j + (yNMask & PrimeY), k + (zNMask & PrimeZ), x0, y0, z0);

        double x1 = xi - 0.5d;
        double y1 = yi - 0.5d;
        double z1 = zi - 0.5d;
        double a1 = 0.75d - x1 * x1 - y1 * y1 - z1 * z1;
        value += (a1 * a1) * (a1 * a1) * GradCoord(seed2, i + PrimeX, j + PrimeY, k + PrimeZ, x1, y1, z1);

        double xAFlipMask0 = ((xNMask | 1) << 1) * x1;
        double yAFlipMask0 = ((yNMask | 1) << 1) * y1;
        double zAFlipMask0 = ((zNMask | 1) << 1) * z1;
        double xAFlipMask1 = (-2 - (xNMask << 2)) * x1 - 1.0d;
        double yAFlipMask1 = (-2 - (yNMask << 2)) * y1 - 1.0d;
        double zAFlipMask1 = (-2 - (zNMask << 2)) * z1 - 1.0d;

        boolean skip5 = false;
        double a2 = xAFlipMask0 + a0;
        if (a2 > 0) {
            double x2 = x0 - (xNMask | 1);
            double y2 = y0;
            double z2 = z0;
            value += (a2 * a2) * (a2 * a2) * GradCoord(seed, i + (~xNMask & PrimeX), j + (yNMask & PrimeY), k + (zNMask & PrimeZ), x2, y2, z2);
        } else {
            double a3 = yAFlipMask0 + zAFlipMask0 + a0;
            if (a3 > 0) {
                double x3 = x0;
                double y3 = y0 - (yNMask | 1);
                double z3 = z0 - (zNMask | 1);
                value += (a3 * a3) * (a3 * a3) * GradCoord(seed, i + (xNMask & PrimeX), j + (~yNMask & PrimeY), k + (~zNMask & PrimeZ), x3, y3, z3);
            }

            double a4 = xAFlipMask1 + a1;
            if (a4 > 0) {
                double x4 = (xNMask | 1) + x1;
                double y4 = y1;
                double z4 = z1;
                value += (a4 * a4) * (a4 * a4) * GradCoord(seed2, i + (xNMask & (PrimeX * 2)), j + PrimeY, k + PrimeZ, x4, y4, z4);
                skip5 = true;
            }
        }

        boolean skip9 = false;
        double a6 = yAFlipMask0 + a0;
        if (a6 > 0) {
            double x6 = x0;
            double y6 = y0 - (yNMask | 1);
            double z6 = z0;
            value += (a6 * a6) * (a6 * a6) * GradCoord(seed, i + (xNMask & PrimeX), j + (~yNMask & PrimeY), k + (zNMask & PrimeZ), x6, y6, z6);
        } else {
            double a7 = xAFlipMask0 + zAFlipMask0 + a0;
            if (a7 > 0) {
                double x7 = x0 - (xNMask | 1);
                double y7 = y0;
                double z7 = z0 - (zNMask | 1);
                value += (a7 * a7) * (a7 * a7) * GradCoord(seed, i + (~xNMask & PrimeX), j + (yNMask & PrimeY), k + (~zNMask & PrimeZ), x7, y7, z7);
            }

            double a8 = yAFlipMask1 + a1;
            if (a8 > 0) {
                double x8 = x1;
                double y8 = (yNMask | 1) + y1;
                double z8 = z1;
                value += (a8 * a8) * (a8 * a8) * GradCoord(seed2, i + PrimeX, j + (yNMask & (PrimeY << 1)), k + PrimeZ, x8, y8, z8);
                skip9 = true;
            }
        }

        boolean skipD = false;
        double aA = zAFlipMask0 + a0;
        if (aA > 0) {
            double xA = x0;
            double yA = y0;
            double zA = z0 - (zNMask | 1);
            value += (aA * aA) * (aA * aA) * GradCoord(seed, i + (xNMask & PrimeX), j + (yNMask & PrimeY), k + (~zNMask & PrimeZ), xA, yA, zA);
        } else {
            double aB = xAFlipMask0 + yAFlipMask0 + a0;
            if (aB > 0) {
                double xB = x0 - (xNMask | 1);
                double yB = y0 - (yNMask | 1);
                double zB = z0;
                value += (aB * aB) * (aB * aB) * GradCoord(seed, i + (~xNMask & PrimeX), j + (~yNMask & PrimeY), k + (zNMask & PrimeZ), xB, yB, zB);
            }

            double aC = zAFlipMask1 + a1;
            if (aC > 0) {
                double xC = x1;
                double yC = y1;
                double zC = (zNMask | 1) + z1;
                value += (aC * aC) * (aC * aC) * GradCoord(seed2, i + PrimeX, j + PrimeY, k + (zNMask & (PrimeZ << 1)), xC, yC, zC);
                skipD = true;
            }
        }

        if (!skip5) {
            double a5 = yAFlipMask1 + zAFlipMask1 + a1;
            if (a5 > 0) {
                double x5 = x1;
                double y5 = (yNMask | 1) + y1;
                double z5 = (zNMask | 1) + z1;
                value += (a5 * a5) * (a5 * a5) * GradCoord(seed2, i + PrimeX, j + (yNMask & (PrimeY << 1)), k + (zNMask & (PrimeZ << 1)), x5, y5, z5);
            }
        }

        if (!skip9) {
            double a9 = xAFlipMask1 + zAFlipMask1 + a1;
            if (a9 > 0) {
                double x9 = (xNMask | 1) + x1;
                double y9 = y1;
                double z9 = (zNMask | 1) + z1;
                value += (a9 * a9) * (a9 * a9) * GradCoord(seed2, i + (xNMask & (PrimeX * 2)), j + PrimeY, k + (zNMask & (PrimeZ << 1)), x9, y9, z9);
            }
        }

        if (!skipD) {
            double aD = xAFlipMask1 + yAFlipMask1 + a1;
            if (aD > 0) {
                double xD = (xNMask | 1) + x1;
                double yD = (yNMask | 1) + y1;
                double zD = z1;
                value += (aD * aD) * (aD * aD) * GradCoord(seed2, i + (xNMask & (PrimeX << 1)), j + (yNMask & (PrimeY << 1)), k + PrimeZ, xD, yD, zD);
            }
        }

        return value * 9.046026385208288d;
    }

    private double SingleCellular(long seed, double x, double y) {
        int xr = FastRound(x);
        int yr = FastRound(y);

        double distance0 = Double.MAX_VALUE;
        double distance1 = Double.MAX_VALUE;
        int closestHash = 0;

        double cellularJitter = 0.43701595d * mCellularJitterModifier;

        int xPrimed = (xr - 1) * PrimeX;
        int yPrimedBase = (yr - 1) * PrimeY;

        switch (mCellularDistanceFunction) {
            default:
            case Euclidean:
            case EuclideanSq:
                for (int xi = xr - 1; xi <= xr + 1; xi++) {
                    int yPrimed = yPrimedBase;

                    for (int yi = yr - 1; yi <= yr + 1; yi++) {
                        int hash = Hash(seed, xPrimed, yPrimed);
                        int idx = hash & (255 << 1);

                        double vecX = (double) (xi - x) + RandVecs2D[idx] * cellularJitter;
                        double vecY = (double) (yi - y) + RandVecs2D[idx | 1] * cellularJitter;

                        double newDistance = vecX * vecX + vecY * vecY;

                        distance1 = FastMax(FastMin(distance1, newDistance), distance0);
                        if (newDistance < distance0) {
                            distance0 = newDistance;
                            closestHash = hash;
                        }
                        yPrimed += PrimeY;
                    }
                    xPrimed += PrimeX;
                }
                break;
            case Manhattan:
                for (int xi = xr - 1; xi <= xr + 1; xi++) {
                    int yPrimed = yPrimedBase;

                    for (int yi = yr - 1; yi <= yr + 1; yi++) {
                        int hash = Hash(seed, xPrimed, yPrimed);
                        int idx = hash & (255 << 1);

                        double vecX = (double) (xi - x) + RandVecs2D[idx] * cellularJitter;
                        double vecY = (double) (yi - y) + RandVecs2D[idx | 1] * cellularJitter;

                        double newDistance = FastAbs(vecX) + FastAbs(vecY);

                        distance1 = FastMax(FastMin(distance1, newDistance), distance0);
                        if (newDistance < distance0) {
                            distance0 = newDistance;
                            closestHash = hash;
                        }
                        yPrimed += PrimeY;
                    }
                    xPrimed += PrimeX;
                }
                break;
            case Hybrid:
                for (int xi = xr - 1; xi <= xr + 1; xi++) {
                    int yPrimed = yPrimedBase;

                    for (int yi = yr - 1; yi <= yr + 1; yi++) {
                        int hash = Hash(seed, xPrimed, yPrimed);
                        int idx = hash & (255 << 1);

                        double vecX = (double) (xi - x) + RandVecs2D[idx] * cellularJitter;
                        double vecY = (double) (yi - y) + RandVecs2D[idx | 1] * cellularJitter;

                        double newDistance = (FastAbs(vecX) + FastAbs(vecY)) + (vecX * vecX + vecY * vecY);

                        distance1 = FastMax(FastMin(distance1, newDistance), distance0);
                        if (newDistance < distance0) {
                            distance0 = newDistance;
                            closestHash = hash;
                        }
                        yPrimed += PrimeY;
                    }
                    xPrimed += PrimeX;
                }
                break;
        }

        if (mCellularDistanceFunction == CellularDistanceFunction.Euclidean && mCellularReturnType != CellularReturnType.CellValue) {
            distance0 = FastSqrt(distance0);

            if (mCellularReturnType != CellularReturnType.CellValue) {
                distance1 = FastSqrt(distance1);
            }
        }

        switch (mCellularReturnType) {
            case CellValue:
                return closestHash * (1 / 2147483648.0d);
            case Distance:
                return distance0 - 1;
            case Distance2:
                return distance1 - 1;
            case Distance2Add:
                return (distance1 + distance0) * 0.5d - 1;
            case Distance2Sub:
                return distance1 - distance0 - 1;
            case Distance2Mul:
                return distance1 * distance0 * 0.5d - 1;
            case Distance2Div:
                return distance0 / distance1 - 1;
            default:
                return 0;
        }
    }

    private double SingleCellular(long seed, double x, double y, double z) {
        int xr = FastRound(x);
        int yr = FastRound(y);
        int zr = FastRound(z);

        double distance0 = Double.MAX_VALUE;
        double distance1 = Double.MAX_VALUE;
        int closestHash = 0;

        double cellularJitter = 0.39614353d * mCellularJitterModifier;

        int xPrimed = (xr - 1) * PrimeX;
        int yPrimedBase = (yr - 1) * PrimeY;
        int zPrimedBase = (zr - 1) * PrimeZ;

        switch (mCellularDistanceFunction) {
            case Euclidean:
            case EuclideanSq:
                for (int xi = xr - 1; xi <= xr + 1; xi++) {
                    int yPrimed = yPrimedBase;

                    for (int yi = yr - 1; yi <= yr + 1; yi++) {
                        int zPrimed = zPrimedBase;

                        for (int zi = zr - 1; zi <= zr + 1; zi++) {
                            int hash = Hash(seed, xPrimed, yPrimed, zPrimed);
                            int idx = hash & (255 << 2);

                            double vecX = (double) (xi - x) + RandVecs3D[idx] * cellularJitter;
                            double vecY = (double) (yi - y) + RandVecs3D[idx | 1] * cellularJitter;
                            double vecZ = (double) (zi - z) + RandVecs3D[idx | 2] * cellularJitter;

                            double newDistance = vecX * vecX + vecY * vecY + vecZ * vecZ;

                            distance1 = FastMax(FastMin(distance1, newDistance), distance0);
                            if (newDistance < distance0) {
                                distance0 = newDistance;
                                closestHash = hash;
                            }
                            zPrimed += PrimeZ;
                        }
                        yPrimed += PrimeY;
                    }
                    xPrimed += PrimeX;
                }
                break;
            case Manhattan:
                for (int xi = xr - 1; xi <= xr + 1; xi++) {
                    int yPrimed = yPrimedBase;

                    for (int yi = yr - 1; yi <= yr + 1; yi++) {
                        int zPrimed = zPrimedBase;

                        for (int zi = zr - 1; zi <= zr + 1; zi++) {
                            int hash = Hash(seed, xPrimed, yPrimed, zPrimed);
                            int idx = hash & (255 << 2);

                            double vecX = (double) (xi - x) + RandVecs3D[idx] * cellularJitter;
                            double vecY = (double) (yi - y) + RandVecs3D[idx | 1] * cellularJitter;
                            double vecZ = (double) (zi - z) + RandVecs3D[idx | 2] * cellularJitter;

                            double newDistance = FastAbs(vecX) + FastAbs(vecY) + FastAbs(vecZ);

                            distance1 = FastMax(FastMin(distance1, newDistance), distance0);
                            if (newDistance < distance0) {
                                distance0 = newDistance;
                                closestHash = hash;
                            }
                            zPrimed += PrimeZ;
                        }
                        yPrimed += PrimeY;
                    }
                    xPrimed += PrimeX;
                }
                break;
            case Hybrid:
                for (int xi = xr - 1; xi <= xr + 1; xi++) {
                    int yPrimed = yPrimedBase;

                    for (int yi = yr - 1; yi <= yr + 1; yi++) {
                        int zPrimed = zPrimedBase;

                        for (int zi = zr - 1; zi <= zr + 1; zi++) {
                            int hash = Hash(seed, xPrimed, yPrimed, zPrimed);
                            int idx = hash & (255 << 2);

                            double vecX = (double) (xi - x) + RandVecs3D[idx] * cellularJitter;
                            double vecY = (double) (yi - y) + RandVecs3D[idx | 1] * cellularJitter;
                            double vecZ = (double) (zi - z) + RandVecs3D[idx | 2] * cellularJitter;

                            double newDistance = (FastAbs(vecX) + FastAbs(vecY) + FastAbs(vecZ)) + (vecX * vecX + vecY * vecY + vecZ * vecZ);

                            distance1 = FastMax(FastMin(distance1, newDistance), distance0);
                            if (newDistance < distance0) {
                                distance0 = newDistance;
                                closestHash = hash;
                            }
                            zPrimed += PrimeZ;
                        }
                        yPrimed += PrimeY;
                    }
                    xPrimed += PrimeX;
                }
                break;
            default:
                break;
        }

        if (mCellularDistanceFunction == CellularDistanceFunction.Euclidean && mCellularReturnType != CellularReturnType.CellValue) {
            distance0 = FastSqrt(distance0);

            if (mCellularReturnType != CellularReturnType.CellValue) {
                distance1 = FastSqrt(distance1);
            }
        }

        switch (mCellularReturnType) {
            case CellValue:
                return closestHash * (1 / 2147483648.0d);
            case Distance:
                return distance0 - 1;
            case Distance2:
                return distance1 - 1;
            case Distance2Add:
                return (distance1 + distance0) * 0.5d - 1;
            case Distance2Sub:
                return distance1 - distance0 - 1;
            case Distance2Mul:
                return distance1 * distance0 * 0.5d - 1;
            case Distance2Div:
                return distance0 / distance1 - 1;
            default:
                return 0;
        }
    }

    private double SinglePerlin(long seed, double x, double y) {
        int x0 = FastFloor(x);
        int y0 = FastFloor(y);

        double xd0 = (double) (x - x0);
        double yd0 = (double) (y - y0);
        double xd1 = xd0 - 1;
        double yd1 = yd0 - 1;

        double xs = InterpQuintic(xd0);
        double ys = InterpQuintic(yd0);

        x0 *= PrimeX;
        y0 *= PrimeY;
        int x1 = x0 + PrimeX;
        int y1 = y0 + PrimeY;

        double xf0 = Lerp(GradCoord(seed, x0, y0, xd0, yd0), GradCoord(seed, x1, y0, xd1, yd0), xs);
        double xf1 = Lerp(GradCoord(seed, x0, y1, xd0, yd1), GradCoord(seed, x1, y1, xd1, yd1), xs);

        return Lerp(xf0, xf1, ys) * 1.4247691104677813d;
    }

    private double SinglePerlin(long seed, double x, double y, double z) {
        int x0 = FastFloor(x);
        int y0 = FastFloor(y);
        int z0 = FastFloor(z);

        double xd0 = (double) (x - x0);
        double yd0 = (double) (y - y0);
        double zd0 = (double) (z - z0);
        double xd1 = xd0 - 1;
        double yd1 = yd0 - 1;
        double zd1 = zd0 - 1;

        double xs = InterpQuintic(xd0);
        double ys = InterpQuintic(yd0);
        double zs = InterpQuintic(zd0);

        x0 *= PrimeX;
        y0 *= PrimeY;
        z0 *= PrimeZ;
        int x1 = x0 + PrimeX;
        int y1 = y0 + PrimeY;
        int z1 = z0 + PrimeZ;

        double xf00 = Lerp(GradCoord(seed, x0, y0, z0, xd0, yd0, zd0), GradCoord(seed, x1, y0, z0, xd1, yd0, zd0), xs);
        double xf10 = Lerp(GradCoord(seed, x0, y1, z0, xd0, yd1, zd0), GradCoord(seed, x1, y1, z0, xd1, yd1, zd0), xs);
        double xf01 = Lerp(GradCoord(seed, x0, y0, z1, xd0, yd0, zd1), GradCoord(seed, x1, y0, z1, xd1, yd0, zd1), xs);
        double xf11 = Lerp(GradCoord(seed, x0, y1, z1, xd0, yd1, zd1), GradCoord(seed, x1, y1, z1, xd1, yd1, zd1), xs);

        double yf0 = Lerp(xf00, xf10, ys);
        double yf1 = Lerp(xf01, xf11, ys);

        return Lerp(yf0, yf1, zs) * 0.964921414852142333984375d;
    }

    private double SingleValueCubic(long seed, double x, double y) {
        int x1 = FastFloor(x);
        int y1 = FastFloor(y);

        double xs = (double) (x - x1);
        double ys = (double) (y - y1);

        x1 *= PrimeX;
        y1 *= PrimeY;
        int x0 = x1 - PrimeX;
        int y0 = y1 - PrimeY;
        int x2 = x1 + PrimeX;
        int y2 = y1 + PrimeY;
        int x3 = x1 + (PrimeX << 1);
        int y3 = y1 + (PrimeY << 1);

        return CubicLerp(CubicLerp(ValCoord(seed, x0, y0), ValCoord(seed, x1, y0), ValCoord(seed, x2, y0), ValCoord(seed, x3, y0), xs), CubicLerp(ValCoord(seed, x0, y1), ValCoord(seed, x1, y1), ValCoord(seed, x2, y1), ValCoord(seed, x3, y1), xs), CubicLerp(ValCoord(seed, x0, y2), ValCoord(seed, x1, y2), ValCoord(seed, x2, y2), ValCoord(seed, x3, y2), xs), CubicLerp(ValCoord(seed, x0, y3), ValCoord(seed, x1, y3), ValCoord(seed, x2, y3), ValCoord(seed, x3, y3), xs), ys) * (1 / (1.5d * 1.5d));
    }

    private double SingleValueCubic(long seed, double x, double y, double z) {
        int x1 = FastFloor(x);
        int y1 = FastFloor(y);
        int z1 = FastFloor(z);

        double xs = (double) (x - x1);
        double ys = (double) (y - y1);
        double zs = (double) (z - z1);

        x1 *= PrimeX;
        y1 *= PrimeY;
        z1 *= PrimeZ;

        int x0 = x1 - PrimeX;
        int y0 = y1 - PrimeY;
        int z0 = z1 - PrimeZ;
        int x2 = x1 + PrimeX;
        int y2 = y1 + PrimeY;
        int z2 = z1 + PrimeZ;
        int x3 = x1 + (PrimeX << 1);
        int y3 = y1 + (PrimeY << 1);
        int z3 = z1 + (PrimeZ << 1);

        return CubicLerp(CubicLerp(CubicLerp(ValCoord(seed, x0, y0, z0), ValCoord(seed, x1, y0, z0), ValCoord(seed, x2, y0, z0), ValCoord(seed, x3, y0, z0), xs), CubicLerp(ValCoord(seed, x0, y1, z0), ValCoord(seed, x1, y1, z0), ValCoord(seed, x2, y1, z0), ValCoord(seed, x3, y1, z0), xs), CubicLerp(ValCoord(seed, x0, y2, z0), ValCoord(seed, x1, y2, z0), ValCoord(seed, x2, y2, z0), ValCoord(seed, x3, y2, z0), xs), CubicLerp(ValCoord(seed, x0, y3, z0), ValCoord(seed, x1, y3, z0), ValCoord(seed, x2, y3, z0), ValCoord(seed, x3, y3, z0), xs), ys), CubicLerp(CubicLerp(ValCoord(seed, x0, y0, z1), ValCoord(seed, x1, y0, z1), ValCoord(seed, x2, y0, z1), ValCoord(seed, x3, y0, z1), xs), CubicLerp(ValCoord(seed, x0, y1, z1), ValCoord(seed, x1, y1, z1), ValCoord(seed, x2, y1, z1), ValCoord(seed, x3, y1, z1), xs), CubicLerp(ValCoord(seed, x0, y2, z1), ValCoord(seed, x1, y2, z1), ValCoord(seed, x2, y2, z1), ValCoord(seed, x3, y2, z1), xs), CubicLerp(ValCoord(seed, x0, y3, z1), ValCoord(seed, x1, y3, z1), ValCoord(seed, x2, y3, z1), ValCoord(seed, x3, y3, z1), xs), ys), CubicLerp(CubicLerp(ValCoord(seed, x0, y0, z2), ValCoord(seed, x1, y0, z2), ValCoord(seed, x2, y0, z2), ValCoord(seed, x3, y0, z2), xs), CubicLerp(ValCoord(seed, x0, y1, z2), ValCoord(seed, x1, y1, z2), ValCoord(seed, x2, y1, z2), ValCoord(seed, x3, y1, z2), xs), CubicLerp(ValCoord(seed, x0, y2, z2), ValCoord(seed, x1, y2, z2), ValCoord(seed, x2, y2, z2), ValCoord(seed, x3, y2, z2), xs), CubicLerp(ValCoord(seed, x0, y3, z2), ValCoord(seed, x1, y3, z2), ValCoord(seed, x2, y3, z2), ValCoord(seed, x3, y3, z2), xs), ys), CubicLerp(CubicLerp(ValCoord(seed, x0, y0, z3), ValCoord(seed, x1, y0, z3), ValCoord(seed, x2, y0, z3), ValCoord(seed, x3, y0, z3), xs), CubicLerp(ValCoord(seed, x0, y1, z3), ValCoord(seed, x1, y1, z3), ValCoord(seed, x2, y1, z3), ValCoord(seed, x3, y1, z3), xs), CubicLerp(ValCoord(seed, x0, y2, z3), ValCoord(seed, x1, y2, z3), ValCoord(seed, x2, y2, z3), ValCoord(seed, x3, y2, z3), xs), CubicLerp(ValCoord(seed, x0, y3, z3), ValCoord(seed, x1, y3, z3), ValCoord(seed, x2, y3, z3), ValCoord(seed, x3, y3, z3), xs), ys), zs) * (1 / (1.5d * 1.5d * 1.5d));
    }

    private double SingleValue(long seed, double x, double y) {
        int x0 = FastFloor(x);
        int y0 = FastFloor(y);

        double xs = InterpHermite((double) (x - x0));
        double ys = InterpHermite((double) (y - y0));

        x0 *= PrimeX;
        y0 *= PrimeY;
        int x1 = x0 + PrimeX;
        int y1 = y0 + PrimeY;

        double xf0 = Lerp(ValCoord(seed, x0, y0), ValCoord(seed, x1, y0), xs);
        double xf1 = Lerp(ValCoord(seed, x0, y1), ValCoord(seed, x1, y1), xs);

        return Lerp(xf0, xf1, ys);
    }

    private double SingleValue(long seed, double x, double y, double z) {
        int x0 = FastFloor(x);
        int y0 = FastFloor(y);
        int z0 = FastFloor(z);

        double xs = InterpHermite((double) (x - x0));
        double ys = InterpHermite((double) (y - y0));
        double zs = InterpHermite((double) (z - z0));

        x0 *= PrimeX;
        y0 *= PrimeY;
        z0 *= PrimeZ;
        int x1 = x0 + PrimeX;
        int y1 = y0 + PrimeY;
        int z1 = z0 + PrimeZ;

        double xf00 = Lerp(ValCoord(seed, x0, y0, z0), ValCoord(seed, x1, y0, z0), xs);
        double xf10 = Lerp(ValCoord(seed, x0, y1, z0), ValCoord(seed, x1, y1, z0), xs);
        double xf01 = Lerp(ValCoord(seed, x0, y0, z1), ValCoord(seed, x1, y0, z1), xs);
        double xf11 = Lerp(ValCoord(seed, x0, y1, z1), ValCoord(seed, x1, y1, z1), xs);

        double yf0 = Lerp(xf00, xf10, ys);
        double yf1 = Lerp(xf01, xf11, ys);

        return Lerp(yf0, yf1, zs);
    }

    private void DoSingleDomainWarp(long seed, double amp, double freq, double x, double y, Vector2 coord) {
        switch (mDomainWarpType) {
            case OpenSimplex2:
                SingleDomainWarpSimplexGradient(seed, amp * 38.283687591552734375d, freq, x, y, coord, false);
                break;
            case OpenSimplex2Reduced:
                SingleDomainWarpSimplexGradient(seed, amp * 16.0d, freq, x, y, coord, true);
                break;
            case BasicGrid:
                SingleDomainWarpBasicGrid(seed, amp, freq, x, y, coord);
                break;
        }
    }

    private void DoSingleDomainWarp(long seed, double amp, double freq, double x, double y, double z, Vector3 coord) {
        switch (mDomainWarpType) {
            case OpenSimplex2:
                SingleDomainWarpOpenSimplex2Gradient(seed, amp * 32.69428253173828125d, freq, x, y, z, coord, false);
                break;
            case OpenSimplex2Reduced:
                SingleDomainWarpOpenSimplex2Gradient(seed, amp * 7.71604938271605d, freq, x, y, z, coord, true);
                break;
            case BasicGrid:
                SingleDomainWarpBasicGrid(seed, amp, freq, x, y, z, coord);
                break;
        }
    }

    private void DomainWarpSingle(Vector2 coord, long eSeed) {
        long seed = mSeed;
        double amp = mDomainWarpAmp * mFractalBounding;
        double freq = mFrequency;

        double xs = coord.x;
        double ys = coord.y;
        switch (mDomainWarpType) {
            case OpenSimplex2:
            case OpenSimplex2Reduced: {
                final double SQRT3 = (double) 1.7320508075688772935274463415059;
                final double F2 = 0.5d * (SQRT3 - 1);
                double t = (xs + ys) * F2;
                xs += t;
                ys += t;
            }
            break;
            default:
                break;
        }

        DoSingleDomainWarp(seed, amp, freq, xs, ys, coord);
    }

    private void DomainWarpSingle(Vector3 coord, long eSeed) {
        long seed = mSeed;
        double amp = mDomainWarpAmp * mFractalBounding;
        double freq = mFrequency;

        double xs = coord.x;
        double ys = coord.y;
        double zs = coord.z;
        switch (mWarpTransformType3D) {
            case ImproveXYPlanes: {
                double xy = xs + ys;
                double s2 = xy * -(double) 0.211324865405187;
                zs *= (double) 0.577350269189626;
                xs += s2 - zs;
                ys = ys + s2 - zs;
                zs += xy * (double) 0.577350269189626;
            }
            break;
            case ImproveXZPlanes: {
                double xz = xs + zs;
                double s2 = xz * -(double) 0.211324865405187;
                ys *= (double) 0.577350269189626;
                xs += s2 - ys;
                zs += s2 - ys;
                ys += xz * (double) 0.577350269189626;
            }
            break;
            case DefaultOpenSimplex2: {
                final double R3 = (double) (2.0 / 3.0);
                double r = (xs + ys + zs) * R3;
                xs = r - xs;
                ys = r - ys;
                zs = r - zs;
            }
            break;
            default:
                break;
        }

        DoSingleDomainWarp(seed, amp, freq, xs, ys, zs, coord);
    }

    private void DomainWarpFractalProgressive(Vector2 coord, long eSeed) {
        long seed = mSeed;
        double amp = mDomainWarpAmp * mFractalBounding;
        double freq = mFrequency;

        for (int i = 0; i < mOctaves; i++) {
            double xs = coord.x;
            double ys = coord.y;
            switch (mDomainWarpType) {
                case OpenSimplex2:
                case OpenSimplex2Reduced: {
                    final double SQRT3 = (double) 1.7320508075688772935274463415059;
                    final double F2 = 0.5d * (SQRT3 - 1);
                    double t = (xs + ys) * F2;
                    xs += t;
                    ys += t;
                }
                break;
                default:
                    break;
            }

            DoSingleDomainWarp(seed, amp, freq, xs, ys, coord);

            seed++;
            amp *= mGain;
            freq *= mLacunarity;
        }
    }

    private void DomainWarpFractalProgressive(Vector3 coord, long eSeed) {
        long seed = mSeed;
        double amp = mDomainWarpAmp * mFractalBounding;
        double freq = mFrequency;

        for (int i = 0; i < mOctaves; i++) {
            double xs = coord.x;
            double ys = coord.y;
            double zs = coord.z;
            switch (mWarpTransformType3D) {
                case ImproveXYPlanes: {
                    double xy = xs + ys;
                    double s2 = xy * -(double) 0.211324865405187;
                    zs *= (double) 0.577350269189626;
                    xs += s2 - zs;
                    ys = ys + s2 - zs;
                    zs += xy * (double) 0.577350269189626;
                }
                break;
                case ImproveXZPlanes: {
                    double xz = xs + zs;
                    double s2 = xz * -(double) 0.211324865405187;
                    ys *= (double) 0.577350269189626;
                    xs += s2 - ys;
                    zs += s2 - ys;
                    ys += xz * (double) 0.577350269189626;
                }
                break;
                case DefaultOpenSimplex2: {
                    final double R3 = (double) (2.0 / 3.0);
                    double r = (xs + ys + zs) * R3;
                    xs = r - xs;
                    ys = r - ys;
                    zs = r - zs;
                }
                break;
                default:
                    break;
            }

            DoSingleDomainWarp(seed, amp, freq, xs, ys, zs, coord);

            seed++;
            amp *= mGain;
            freq *= mLacunarity;
        }
    }

    private void DomainWarpFractalIndependent(Vector2 coord, long eSeed) {
        double xs = coord.x;
        double ys = coord.y;
        switch (mDomainWarpType) {
            case OpenSimplex2:
            case OpenSimplex2Reduced: {
                final double SQRT3 = (double) 1.7320508075688772935274463415059;
                final double F2 = 0.5d * (SQRT3 - 1);
                double t = (xs + ys) * F2;
                xs += t;
                ys += t;
            }
            break;
            default:
                break;
        }

        long seed = mSeed;
        double amp = mDomainWarpAmp * mFractalBounding;
        double freq = mFrequency;

        for (int i = 0; i < mOctaves; i++) {
            DoSingleDomainWarp(seed, amp, freq, xs, ys, coord);

            seed++;
            amp *= mGain;
            freq *= mLacunarity;
        }
    }

    private void DomainWarpFractalIndependent(Vector3 coord, long eSeed) {
        double xs = coord.x;
        double ys = coord.y;
        double zs = coord.z;
        switch (mWarpTransformType3D) {
            case ImproveXYPlanes: {
                double xy = xs + ys;
                double s2 = xy * -(double) 0.211324865405187;
                zs *= (double) 0.577350269189626;
                xs += s2 - zs;
                ys = ys + s2 - zs;
                zs += xy * (double) 0.577350269189626;
            }
            break;
            case ImproveXZPlanes: {
                double xz = xs + zs;
                double s2 = xz * -(double) 0.211324865405187;
                ys *= (double) 0.577350269189626;
                xs += s2 - ys;
                zs += s2 - ys;
                ys += xz * (double) 0.577350269189626;
            }
            break;
            case DefaultOpenSimplex2: {
                final double R3 = (double) (2.0 / 3.0);
                double r = (xs + ys + zs) * R3;
                xs = r - xs;
                ys = r - ys;
                zs = r - zs;
            }
            break;
            default:
                break;
        }

        long seed = mSeed;
        double amp = mDomainWarpAmp * mFractalBounding;
        double freq = mFrequency;

        for (int i = 0; i < mOctaves; i++) {
            DoSingleDomainWarp(seed, amp, freq, xs, ys, zs, coord);

            seed++;
            amp *= mGain;
            freq *= mLacunarity;
        }
    }

    private void SingleDomainWarpBasicGrid(long seed, double warpAmp, double frequency, double x, double y, Vector2 coord) {
        double xf = x * frequency;
        double yf = y * frequency;

        int x0 = FastFloor(xf);
        int y0 = FastFloor(yf);

        double xs = InterpHermite((double) (xf - x0));
        double ys = InterpHermite((double) (yf - y0));

        x0 *= PrimeX;
        y0 *= PrimeY;
        int x1 = x0 + PrimeX;
        int y1 = y0 + PrimeY;

        int hash0 = Hash(seed, x0, y0) & (255 << 1);
        int hash1 = Hash(seed, x1, y0) & (255 << 1);

        double lx0x = Lerp(RandVecs2D[hash0], RandVecs2D[hash1], xs);
        double ly0x = Lerp(RandVecs2D[hash0 | 1], RandVecs2D[hash1 | 1], xs);

        hash0 = Hash(seed, x0, y1) & (255 << 1);
        hash1 = Hash(seed, x1, y1) & (255 << 1);

        double lx1x = Lerp(RandVecs2D[hash0], RandVecs2D[hash1], xs);
        double ly1x = Lerp(RandVecs2D[hash0 | 1], RandVecs2D[hash1 | 1], xs);

        coord.x += Lerp(lx0x, lx1x, ys) * warpAmp;
        coord.y += Lerp(ly0x, ly1x, ys) * warpAmp;
    }

    private void SingleDomainWarpBasicGrid(long seed, double warpAmp, double frequency, double x, double y, double z, Vector3 coord) {
        double xf = x * frequency;
        double yf = y * frequency;
        double zf = z * frequency;

        int x0 = FastFloor(xf);
        int y0 = FastFloor(yf);
        int z0 = FastFloor(zf);

        double xs = InterpHermite((double) (xf - x0));
        double ys = InterpHermite((double) (yf - y0));
        double zs = InterpHermite((double) (zf - z0));

        x0 *= PrimeX;
        y0 *= PrimeY;
        z0 *= PrimeZ;
        int x1 = x0 + PrimeX;
        int y1 = y0 + PrimeY;
        int z1 = z0 + PrimeZ;

        int hash0 = Hash(seed, x0, y0, z0) & (255 << 2);
        int hash1 = Hash(seed, x1, y0, z0) & (255 << 2);

        double lx0x = Lerp(RandVecs3D[hash0], RandVecs3D[hash1], xs);
        double ly0x = Lerp(RandVecs3D[hash0 | 1], RandVecs3D[hash1 | 1], xs);
        double lz0x = Lerp(RandVecs3D[hash0 | 2], RandVecs3D[hash1 | 2], xs);

        hash0 = Hash(seed, x0, y1, z0) & (255 << 2);
        hash1 = Hash(seed, x1, y1, z0) & (255 << 2);

        double lx1x = Lerp(RandVecs3D[hash0], RandVecs3D[hash1], xs);
        double ly1x = Lerp(RandVecs3D[hash0 | 1], RandVecs3D[hash1 | 1], xs);
        double lz1x = Lerp(RandVecs3D[hash0 | 2], RandVecs3D[hash1 | 2], xs);

        double lx0y = Lerp(lx0x, lx1x, ys);
        double ly0y = Lerp(ly0x, ly1x, ys);
        double lz0y = Lerp(lz0x, lz1x, ys);

        hash0 = Hash(seed, x0, y0, z1) & (255 << 2);
        hash1 = Hash(seed, x1, y0, z1) & (255 << 2);

        lx0x = Lerp(RandVecs3D[hash0], RandVecs3D[hash1], xs);
        ly0x = Lerp(RandVecs3D[hash0 | 1], RandVecs3D[hash1 | 1], xs);
        lz0x = Lerp(RandVecs3D[hash0 | 2], RandVecs3D[hash1 | 2], xs);

        hash0 = Hash(seed, x0, y1, z1) & (255 << 2);
        hash1 = Hash(seed, x1, y1, z1) & (255 << 2);

        lx1x = Lerp(RandVecs3D[hash0], RandVecs3D[hash1], xs);
        ly1x = Lerp(RandVecs3D[hash0 | 1], RandVecs3D[hash1 | 1], xs);
        lz1x = Lerp(RandVecs3D[hash0 | 2], RandVecs3D[hash1 | 2], xs);

        coord.x += Lerp(lx0y, Lerp(lx0x, lx1x, ys), zs) * warpAmp;
        coord.y += Lerp(ly0y, Lerp(ly0x, ly1x, ys), zs) * warpAmp;
        coord.z += Lerp(lz0y, Lerp(lz0x, lz1x, ys), zs) * warpAmp;
    }

    private void SingleDomainWarpSimplexGradient(long seed, double warpAmp, double frequency, double x, double y, Vector2 coord, boolean outGradOnly) {
        final double SQRT3 = 1.7320508075688772935274463415059d;
        final double G2 = (3 - SQRT3) / 6;

        x *= frequency;
        y *= frequency;

        int i = FastFloor(x);
        int j = FastFloor(y);
        double xi = (double) (x - i);
        double yi = (double) (y - j);

        double t = (xi + yi) * G2;
        double x0 = (double) (xi - t);
        double y0 = (double) (yi - t);

        i *= PrimeX;
        j *= PrimeY;

        double vx, vy;
        vx = vy = 0;

        double a = 0.5d - x0 * x0 - y0 * y0;
        if (a > 0) {
            double aaaa = (a * a) * (a * a);
            double xo, yo;
            if (outGradOnly) {
                int hash = Hash(seed, i, j) & (255 << 1);
                xo = RandVecs2D[hash];
                yo = RandVecs2D[hash | 1];
            } else {
                int hash = Hash(seed, i, j);
                int index1 = hash & (127 << 1);
                int index2 = (hash >> 7) & (255 << 1);
                double xg = Gradients2D[index1];
                double yg = Gradients2D[index1 | 1];
                double value = x0 * xg + y0 * yg;
                double xgo = RandVecs2D[index2];
                double ygo = RandVecs2D[index2 | 1];
                xo = value * xgo;
                yo = value * ygo;
            }
            vx += aaaa * xo;
            vy += aaaa * yo;
        }

        double c = (double) (2 * (1 - 2 * G2) * (1 / G2 - 2)) * t + ((double) (-2 * (1 - 2 * G2) * (1 - 2 * G2)) + a);
        if (c > 0) {
            double x2 = x0 + (2 * (double) G2 - 1);
            double y2 = y0 + (2 * (double) G2 - 1);
            double cccc = (c * c) * (c * c);
            double xo, yo;
            if (outGradOnly) {
                int hash = Hash(seed, i + PrimeX, j + PrimeY) & (255 << 1);
                xo = RandVecs2D[hash];
                yo = RandVecs2D[hash | 1];
            } else {
                int hash = Hash(seed, i + PrimeX, j + PrimeY);
                int index1 = hash & (127 << 1);
                int index2 = (hash >> 7) & (255 << 1);
                double xg = Gradients2D[index1];
                double yg = Gradients2D[index1 | 1];
                double value = x2 * xg + y2 * yg;
                double xgo = RandVecs2D[index2];
                double ygo = RandVecs2D[index2 | 1];
                xo = value * xgo;
                yo = value * ygo;
            }
            vx += cccc * xo;
            vy += cccc * yo;
        }

        if (y0 > x0) {
            double x1 = x0 + (double) G2;
            double y1 = y0 + ((double) G2 - 1);
            double b = 0.5d - x1 * x1 - y1 * y1;
            if (b > 0) {
                double bbbb = (b * b) * (b * b);
                double xo, yo;
                if (outGradOnly) {
                    int hash = Hash(seed, i, j + PrimeY) & (255 << 1);
                    xo = RandVecs2D[hash];
                    yo = RandVecs2D[hash | 1];
                } else {
                    int hash = Hash(seed, i, j + PrimeY);
                    int index1 = hash & (127 << 1);
                    int index2 = (hash >> 7) & (255 << 1);
                    double xg = Gradients2D[index1];
                    double yg = Gradients2D[index1 | 1];
                    double value = x1 * xg + y1 * yg;
                    double xgo = RandVecs2D[index2];
                    double ygo = RandVecs2D[index2 | 1];
                    xo = value * xgo;
                    yo = value * ygo;
                }
                vx += bbbb * xo;
                vy += bbbb * yo;
            }
        } else {
            double x1 = x0 + ((double) G2 - 1);
            double y1 = y0 + (double) G2;
            double b = 0.5d - x1 * x1 - y1 * y1;
            if (b > 0) {
                double bbbb = (b * b) * (b * b);
                double xo, yo;
                if (outGradOnly) {
                    int hash = Hash(seed, i + PrimeX, j) & (255 << 1);
                    xo = RandVecs2D[hash];
                    yo = RandVecs2D[hash | 1];
                } else {
                    int hash = Hash(seed, i + PrimeX, j);
                    int index1 = hash & (127 << 1);
                    int index2 = (hash >> 7) & (255 << 1);
                    double xg = Gradients2D[index1];
                    double yg = Gradients2D[index1 | 1];
                    double value = x1 * xg + y1 * yg;
                    double xgo = RandVecs2D[index2];
                    double ygo = RandVecs2D[index2 | 1];
                    xo = value * xgo;
                    yo = value * ygo;
                }
                vx += bbbb * xo;
                vy += bbbb * yo;
            }
        }

        coord.x += vx * warpAmp;
        coord.y += vy * warpAmp;
    }

    private void SingleDomainWarpOpenSimplex2Gradient(long seed, double warpAmp, double frequency, double x, double y, double z, Vector3 coord, boolean outGradOnly) {
        x *= frequency;
        y *= frequency;
        z *= frequency;

        int i = FastRound(x);
        int j = FastRound(y);
        int k = FastRound(z);
        double x0 = (double) x - i;
        double y0 = (double) y - j;
        double z0 = (double) z - k;

        int xNSign = (int) (-x0 - 1.0d) | 1;
        int yNSign = (int) (-y0 - 1.0d) | 1;
        int zNSign = (int) (-z0 - 1.0d) | 1;

        double ax0 = xNSign * -x0;
        double ay0 = yNSign * -y0;
        double az0 = zNSign * -z0;

        i *= PrimeX;
        j *= PrimeY;
        k *= PrimeZ;

        double vx, vy, vz;
        vx = vy = vz = 0;

        double a = (0.6d - x0 * x0) - (y0 * y0 + z0 * z0);
        for (int l = 0;; l++) {
            if (a > 0) {
                double aaaa = (a * a) * (a * a);
                double xo, yo, zo;
                if (outGradOnly) {
                    int hash = Hash(seed, i, j, k) & (255 << 2);
                    xo = RandVecs3D[hash];
                    yo = RandVecs3D[hash | 1];
                    zo = RandVecs3D[hash | 2];
                } else {
                    int hash = Hash(seed, i, j, k);
                    int index1 = hash & (63 << 2);
                    int index2 = (hash >> 6) & (255 << 2);
                    double xg = Gradients3D[index1];
                    double yg = Gradients3D[index1 | 1];
                    double zg = Gradients3D[index1 | 2];
                    double value = x0 * xg + y0 * yg + z0 * zg;
                    double xgo = RandVecs3D[index2];
                    double ygo = RandVecs3D[index2 | 1];
                    double zgo = RandVecs3D[index2 | 2];
                    xo = value * xgo;
                    yo = value * ygo;
                    zo = value * zgo;
                }
                vx += aaaa * xo;
                vy += aaaa * yo;
                vz += aaaa * zo;
            }

            double b = a;
            int i1 = i;
            int j1 = j;
            int k1 = k;
            double x1 = x0;
            double y1 = y0;
            double z1 = z0;

            if (ax0 >= ay0 && ax0 >= az0) {
                x1 += xNSign;
                b = b + ax0 + ax0;
                i1 -= xNSign * PrimeX;
            } else if (ay0 > ax0 && ay0 >= az0) {
                y1 += yNSign;
                b = b + ay0 + ay0;
                j1 -= yNSign * PrimeY;
            } else {
                z1 += zNSign;
                b = b + az0 + az0;
                k1 -= zNSign * PrimeZ;
            }

            if (b > 1) {
                b -= 1;
                double bbbb = (b * b) * (b * b);
                double xo, yo, zo;
                if (outGradOnly) {
                    int hash = Hash(seed, i1, j1, k1) & (255 << 2);
                    xo = RandVecs3D[hash];
                    yo = RandVecs3D[hash | 1];
                    zo = RandVecs3D[hash | 2];
                } else {
                    int hash = Hash(seed, i1, j1, k1);
                    int index1 = hash & (63 << 2);
                    int index2 = (hash >> 6) & (255 << 2);
                    double xg = Gradients3D[index1];
                    double yg = Gradients3D[index1 | 1];
                    double zg = Gradients3D[index1 | 2];
                    double value = x1 * xg + y1 * yg + z1 * zg;
                    double xgo = RandVecs3D[index2];
                    double ygo = RandVecs3D[index2 | 1];
                    double zgo = RandVecs3D[index2 | 2];
                    xo = value * xgo;
                    yo = value * ygo;
                    zo = value * zgo;
                }
                vx += bbbb * xo;
                vy += bbbb * yo;
                vz += bbbb * zo;
            }

            if (l == 1)
                break;

            ax0 = 0.5d - ax0;
            ay0 = 0.5d - ay0;
            az0 = 0.5d - az0;

            x0 = xNSign * ax0;
            y0 = yNSign * ay0;
            z0 = zNSign * az0;

            a += (0.75d - ax0) - (ay0 + az0);

            i += (xNSign >> 1) & PrimeX;
            j += (yNSign >> 1) & PrimeY;
            k += (zNSign >> 1) & PrimeZ;

            xNSign = -xNSign;
            yNSign = -yNSign;
            zNSign = -zNSign;

            seed += 1293373;
        }

        coord.x += vx * warpAmp;
        coord.y += vy * warpAmp;
        coord.z += vz * warpAmp;
    }

    public static class Vector2 {
        public double x;
        public double y;

        public Vector2(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Vector3 {
        public double x;
        public double y;
        public double z;

        public Vector3(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

}