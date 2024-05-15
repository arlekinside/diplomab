import Params from "../../Params";

function Content(props: { children: any, width?: string }) {
    let width = props.width || '100%';

    return (
        <div style={{
            width: width, margin: '1vh 1px 1vh 1px', padding: '10px', boxSizing: 'border-box',
            backgroundColor: Params.colors.background, borderRadius: "30px", minHeight: '50vh'
        }}>
            {props.children}
        </div>
    );
}

export default Content;